package me.syahdilla.putra.sholeh.storyappdicoding.ui.activity.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import me.syahdilla.putra.sholeh.story.core.UserManager
import me.syahdilla.putra.sholeh.story.core.utils.*
import me.syahdilla.putra.sholeh.storyappdicoding.R
import me.syahdilla.putra.sholeh.storyappdicoding.databinding.ActivityLoginBinding
import me.syahdilla.putra.sholeh.storyappdicoding.ui.activity.BaseActivity
import me.syahdilla.putra.sholeh.storyappdicoding.ui.activity.main.MainActivity
import me.syahdilla.putra.sholeh.storyappdicoding.ui.activity.signup.EMAIL_REGISTER_RESULT
import me.syahdilla.putra.sholeh.storyappdicoding.ui.activity.signup.PASSWORD_REGISTER_RESULT
import me.syahdilla.putra.sholeh.storyappdicoding.ui.activity.signup.SignupActivity
import me.syahdilla.putra.sholeh.storyappdicoding.ui.dialog.LoadingDialog
import me.syahdilla.putra.sholeh.storyappdicoding.ui.event.LoginEvent
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {

    private val viewModel: LoginViewModel by viewModel()
    private val loading: LoadingDialog by inject()
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode != Activity.RESULT_OK) return@registerForActivityResult
        logger.debug("Register result is OK")
        result.data?.run {
            getStringExtra(EMAIL_REGISTER_RESULT)?.let {
                logger.debug("filled email from register result")
                binding.edLoginEmail.setText(it)
            }
            getStringExtra(PASSWORD_REGISTER_RESULT)?.let {
                logger.debug("filled password from register result")
                binding.edLoginPassword.setText(it)
            }
            logger.debug("perform click button login")
            binding.btnLogin.performClick()
        }
    }
    private var keepSplashScreen = true

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().setKeepOnScreenCondition {
            keepSplashScreen
        }
        super.onCreate(savedInstanceState)
    }


    override suspend fun onInitialize(savedInstanceState: Bundle?) = with(binding) {

        root.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus)
                WindowCompat.getInsetsController(window, v).hide(WindowInsetsCompat.Type.ime())
        }

        edLoginEmail.onEmailInvalidView = labelInvalidEmail
        edLoginPassword.onPasswordInvalidView = labelInvalidPassword

        safeLaunch {
            viewModel.state.flowWithLifecycle(lifecycle).distinctUntilChanged().collectLatest {
                if (it != LoginEvent.InProgress) {
                    loading.close()
                    EspressoIdlingResource.decrement()
                }
                when (it) {
                    is LoginEvent.Failure -> Toast.makeText(mThis, it.message, Toast.LENGTH_SHORT).show()
                    is LoginEvent.Success -> {
                        keepSplashScreen = false
                        UserManager.user = it.session
                        MainActivity::class.openActivity(
                            useActivityTransition = false
                        )
                        finish()
                    }
                    LoginEvent.InProgress -> loading.show(mThis)
                    else -> {}
                }
            }
        }

        btnLogin.setOnClickListener {
            if (!edLoginEmail.isInputValid || !edLoginPassword.isInputValid)
                return@setOnClickListener Toast.makeText(mThis, getString(R.string.input_data_invalid), Toast.LENGTH_SHORT).show()
            viewModel.login(
                email = edLoginEmail.getText,
                password = edLoginPassword.getText
            )
        }

        btnSignup.setOnClickListener {
            launcher.open(
                SignupActivity::class,
                useActivityTransition = false,
                banner to "banner",
                edLoginEmail to "email",
                edLoginPassword to "password",
                labelOr to "or",
                btnSignup to "signup",
                btnLogin to "login",
                language to "lang"
            )
        }

        language.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }

        if (!isUITest && viewModel.hasSession()) return@with

        keepSplashScreen = false

        if (!isUITest && animationsEnabled()) animateViews()
    }

    private fun animateViews() = with(binding) {

        root.animateChildViews {
            animateTogether(
                it.animateSlide(),
                it.animateFade()
            )
        }.playSequentially()

        banner.animateInfinite(lifecycle)
    }

    override fun onBackPressed() = finish()

}