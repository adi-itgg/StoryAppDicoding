package me.syahdilla.putra.sholeh.storyappdicoding.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import me.syahdilla.putra.sholeh.storyappdicoding.databinding.DialogLoadingBinding
import org.koin.core.annotation.Factory

@Factory
class LoadingDialog: DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = DialogLoadingBinding.inflate(layoutInflater, container, false).root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        isCancelable = false
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

    fun close() {
        if (dialog?.isShowing == true) dismiss()
    }

    fun show(mActivity: AppCompatActivity) = show(mActivity.supportFragmentManager, this::class.java.simpleName)

}