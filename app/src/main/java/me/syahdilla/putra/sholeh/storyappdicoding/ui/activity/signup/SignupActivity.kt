package me.syahdilla.putra.sholeh.storyappdicoding.ui.activity.signup

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import me.syahdilla.putra.sholeh.storyappdicoding.R
import me.syahdilla.putra.sholeh.storyappdicoding.databinding.ActivitySignupBinding
import me.syahdilla.putra.sholeh.story.core.utils.safeLaunch
import me.syahdilla.putra.sholeh.storyappdicoding.ui.activity.BaseActivity
import me.syahdilla.putra.sholeh.storyappdicoding.ui.dialog.LoadingDialog
import me.syahdilla.putra.sholeh.storyappdicoding.ui.event.DefaultEvent
import me.syahdilla.putra.sholeh.story.core.utils.EspressoIdlingResource
import me.syahdilla.putra.sholeh.story.core.utils.animateFade
import me.syahdilla.putra.sholeh.story.core.utils.animateInfinite
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

const val EMAIL_REGISTER_RESULT = "email_register_result"
const val PASSWORD_REGISTER_RESULT = "password_register_result"

class SignupActivity: BaseActivity<ActivitySignupBinding>(ActivitySignupBinding::inflate) {

    private val viewModel: SignupViewModel by viewModel()
    private val loading: LoadingDialog by inject()

    override suspend fun onInitialize(savedInstanceState: Bundle?) = with(binding) {

        root.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus)
                WindowCompat.getInsetsController(window, v).hide(WindowInsetsCompat.Type.ime())
        }

        edRegisterName.onNameInvalidView = labelInvalidName
        edRegisterEmail.onEmailInvalidView = labelInvalidEmail
        edRegisterPassword.onPasswordInvalidView = labelInvalidPassword


        safeLaunch {
            viewModel.state.flowWithLifecycle(lifecycle).distinctUntilChanged().collectLatest {
                if (it != DefaultEvent.InProgress) loading.close()
                when (it) {
                    is DefaultEvent.Failure -> Toast.makeText(mThis, it.message, Toast.LENGTH_SHORT).show()
                    DefaultEvent.Success -> {
                        Toast.makeText(mThis, getString(R.string.created_account), Toast.LENGTH_SHORT).show()
                        setResult(
                            RESULT_OK, Intent()
                            .putExtra(EMAIL_REGISTER_RESULT, edRegisterEmail.getText)
                            .putExtra(PASSWORD_REGISTER_RESULT, edRegisterPassword.getText)
                        )
                        finish()
                    }
                    DefaultEvent.InProgress -> loading.show(mThis)
                    else -> {}
                }
            }
        }

        btnSignup.setOnClickListener {
            if (!edRegisterName.isInputValid || !edRegisterEmail.isInputValid || !edRegisterPassword.isInputValid)
                return@setOnClickListener Toast.makeText(mThis, getString(R.string.input_data_invalid), Toast.LENGTH_SHORT).show()
            EspressoIdlingResource.increment()
            viewModel.register(
                name = edRegisterName.getText,
                email = edRegisterEmail.getText,
                password = edRegisterPassword.getText
            )
        }

        btnLogin.setOnClickListener {
            onBackPressed()
        }

        language.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }

        labelSignUp.animateFade {
            duration = 500
        }

        banner.animateInfinite(lifecycle)

    }

}