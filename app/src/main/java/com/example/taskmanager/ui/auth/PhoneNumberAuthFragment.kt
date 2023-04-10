package com.example.taskmanager.ui.auth

import android.net.wifi.hotspot2.pps.Credential
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.taskmanager.R
import com.example.taskmanager.databinding.FragmentPhoneNumberAuthBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class PhoneNumberAuthFragment : Fragment() {
    private lateinit var binding: FragmentPhoneNumberAuthBinding
    private lateinit var auth: FirebaseAuth
    private var isReadyToStart = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPhoneNumberAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        binding.apply {
            //showAutoKeyboard(requireContext(), etPhoneNumber)
            btnOk.setOnClickListener {
                if (etPhoneNumber.text.isNotEmpty()) {
                    sendVerificationCode(etPhoneNumber.text.toString())
                } else {
                    etPhoneNumber.error = "Поле для номера телефона пусто!!!"
                }
            }
            btnVerify.setOnClickListener {
                if (etCode.text.isNotEmpty()) {
                    verifyCode(etCode.text.toString())
                } else {
                    etCode.error = "Поле для номера телефона пусто!!!"
                }
            }
        }
    }

    fun verifyCode(code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationID, code)
        signInByCredentials(credential)
    }

    private fun signInByCredentials(credential: PhoneAuthCredential) {
        val mFireBaseAuth = FirebaseAuth.getInstance()
        mFireBaseAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                findNavController().navigateUp()
                isReadyToStart = true
                setFragmentResult(CODE_FOR_RESULT, bundleOf(KEY_BOOLEAN_RESULT to isReadyToStart))
                isReadyToStart = false
            }
        }
    }

    private fun sendVerificationCode(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+996$phoneNumber")
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(requireActivity())
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            val code = credential.smsCode
            if (code != null) {
                PhoneNumberAuthFragment().sendVerificationCode(code) //тут может быть ошибка
            }
        }

        override fun onVerificationFailed(e: FirebaseException) {
            Toast.makeText(requireContext(), "Ошибка проверки", Toast.LENGTH_SHORT).show()
        }

        override fun onCodeSent(
            verificationId: String, token: PhoneAuthProvider.ForceResendingToken
        ) {
            super.onCodeSent(verificationId, token)
            verificationID = verificationId
            Toast.makeText(requireContext(), "Проверка успешно завершена", Toast.LENGTH_SHORT)
                .show()
        }
    }

    companion object {
        lateinit var verificationID: String
        val CODE_FOR_RESULT = "12121"
        val KEY_BOOLEAN_RESULT = "12221"
    }
}