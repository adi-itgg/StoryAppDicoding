package me.syahdilla.putra.sholeh.storyappdicoding.ui.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import androidx.core.view.updatePadding
import androidx.core.widget.addTextChangedListener
import androidx.transition.Slide
import androidx.transition.TransitionManager
import me.syahdilla.putra.sholeh.storyappdicoding.R

enum class EditableType {
    NORMAL,
    NAME,
    EMAIL,
    PASSWORD
}

class EditableTextView: AppCompatEditText, View.OnTouchListener {

    constructor(context: Context) : super(context) {
        initialize()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initializeStyleable(attrs)
        initialize()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initializeStyleable(attrs)
        initialize()
    }

    private lateinit var clearButtonImage: Drawable

    private val passwordTransformationMethod by lazy {
        PasswordTransformationMethod()
    }

    private var _textType = EditableType.NORMAL


    var onNameInvalidView: View? = null
    var onEmailInvalidView: View? = null
    var onPasswordInvalidView: View? = null

    private var _isInputValid = false
    val isInputValid
        get() = _isInputValid
    val getText
        get() = text?.toString() ?: ""

    private val isInputValidAsView
        get() = if (isInputValid || getText.isEmpty()) View.INVISIBLE else View.VISIBLE

    private fun View.validateView() {
        animateViewsChanged(this)
        visibility = isInputValidAsView
    }

    private fun initialize() {
        clearButtonImage = R.drawable.editable_ic_close.getDrawable
        setOnTouchListener(this)

        addTextChangedListener(
            onTextChanged = { s, _, _, _ ->
                if (s.toString().isNotEmpty()) showClearButton() else hideClearButton()
                when(_textType) {
                    EditableType.NORMAL -> {}
                    EditableType.NAME -> {
                        _isInputValid = length() > 3
                        onNameInvalidView?.validateView()
                    }
                    EditableType.EMAIL -> {
                        val text = s.toString()
                        _isInputValid = text.split("@").run {
                            if (size <= 1)
                                return@run false
                            if (!this[1].contains("."))
                                return@run false
                            val sp = this[1].split(".")
                            if (sp.size <= 1)
                                return@run false
                            if (sp[0].length < 2 || sp[1].length < 2)
                                return@run false
                            true
                        }
                        onEmailInvalidView?.validateView()
                    }
                    EditableType.PASSWORD -> {
                        _isInputValid = length() > 7
                        onPasswordInvalidView?.validateView()
                    }
                }
            }
        )

        background = R.drawable.editable_textview_background.getDrawable

        when(_textType) {
            EditableType.NORMAL -> _isInputValid = true
            EditableType.NAME -> {
                maxLines = 1
                hint = context.getString(R.string.hint_name)
            }
            EditableType.EMAIL -> {
                maxLines = 1
                hint = context.getString(R.string.hint_email)
            }
            EditableType.PASSWORD -> {
                maxLines = 1
                transformationMethod = passwordTransformationMethod
                hint = context.getString(R.string.hint_password)
            }
        }

        setButtonDrawables()
    }

    private fun initializeStyleable(attrs: AttributeSet) {
        context.withStyledAttributes(attrs, R.styleable.EditableTextView) {
            _textType = EditableType.values()[getInt(R.styleable.EditableTextView_textType, 0)]
        }
    }

    private fun showClearButton() {
        if (_textType == EditableType.PASSWORD) return setButtonDrawables()
        setButtonDrawables(endOfTheText = clearButtonImage)
    }
    private fun hideClearButton() {
        setButtonDrawables()
    }

    private fun setButtonDrawables(
        startOfTheText: Drawable? =
            when(_textType) {
                EditableType.EMAIL -> R.drawable.editable_ic_email
                EditableType.PASSWORD -> R.drawable.editable_ic_password
                else -> null
            }?.getDrawable,
        topOfTheText:Drawable? = null,
        endOfTheText:Drawable? = if (_textType == EditableType.PASSWORD)
            (if (transformationMethod == null)
                R.drawable.editable_ic_eye
            else
                R.drawable.editable_ic_eye_off).getDrawable
        else null,
        bottomOfTheText: Drawable? = null
    ){
        setCompoundDrawablesWithIntrinsicBounds(
            startOfTheText,
            topOfTheText,
            endOfTheText,
            bottomOfTheText
        )
    }

    private fun animateViewsChanged(
        view: View
    ) = Slide(if (view.visibility == View.VISIBLE) Gravity.END else Gravity.START).apply {
        duration = 400
        addTarget(view)
        TransitionManager.beginDelayedTransition(parent as ViewGroup, this)
    }


    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        if (compoundDrawables[2] == null) return false
        var isRightButtonClicked = false
        val instrinsicWidth = clearButtonImage.intrinsicWidth
        if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
            val clearButtonEnd = (instrinsicWidth + paddingStart).toFloat()
            if (event.x < clearButtonEnd) isRightButtonClicked = true
        } else {
            val clearButtonStart = (width - paddingEnd - instrinsicWidth).toFloat()
            if (event.x > clearButtonStart) isRightButtonClicked = true
        }
        return if (isRightButtonClicked)
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    showClearButton()
                    true
                }
                MotionEvent.ACTION_UP -> {
                    if (_textType == EditableType.PASSWORD) {
                        transformationMethod = if (transformationMethod == null)
                            passwordTransformationMethod else null
                        setSelection(length())
                    }
                    if (_textType != EditableType.PASSWORD && text != null)  text?.clear()
                    hideClearButton()
                    true
                }
                else -> false
            }
        else false
    }

    private var isFirstInitialize = true
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (isFirstInitialize) {
            isFirstInitialize = false
            val dp = resources.getDimensionPixelSize(R.dimen.editable_default_padding)
            updatePadding(left = dp, right = dp)
            compoundDrawablePadding = dp
        }
    }

    private var isFirstInitializeOnDraw = true
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (isFirstInitializeOnDraw) return
        isFirstInitializeOnDraw = true
        when(_textType) {
            EditableType.NORMAL -> _isInputValid = true
            EditableType.NAME -> inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PERSON_NAME
            EditableType.EMAIL -> inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            EditableType.PASSWORD -> inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
    }

    private val Int.getDrawable
        get() = ContextCompat.getDrawable(context, this) as Drawable

}