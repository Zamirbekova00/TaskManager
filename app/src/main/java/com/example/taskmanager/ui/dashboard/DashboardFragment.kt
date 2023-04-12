package com.example.taskmanager.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.taskmanager.data.local.Pref
import com.example.taskmanager.databinding.FragmentDashboardBinding
import com.example.taskmanager.model.Car
import com.example.taskmanager.utils.showToast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private lateinit var pref: Pref
    private lateinit var db: FirebaseFirestore


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pref = Pref(requireContext())
        db = FirebaseFirestore.getInstance()
        binding.btnSave.setOnClickListener {
            saveCar()
        }
    }

    private fun saveCar() {
        val name = binding.etTitle.text.toString()
        val model = binding.etDesc.text.toString()
        val car = Car(name, model)

        db.collection(FirebaseAuth.getInstance().currentUser?.uid.toString()).document().set(car)
            .addOnSuccessListener {
                showToast("Данные сохранены !")
                binding.etTitle.text?.clear()
                binding.etDesc.text?.clear()
            }
            .addOnFailureListener {
                Log.e("kalbusha", "saveCar: " + it.message)
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}