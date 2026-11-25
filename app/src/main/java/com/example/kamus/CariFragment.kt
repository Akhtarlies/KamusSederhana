package com.example.kamus

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class CariFragment : Fragment(R.layout.fragment_cari) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dao = KamusDatabase.getInstance(requireContext()).kataDao()
        val etCari = view.findViewById<EditText>(R.id.etCari)
        val txtKosong = view.findViewById<TextView>(R.id.txtKosong)
        val rvHasil = view.findViewById<RecyclerView>(R.id.rvHasil)

        val adapter = KataAdapter()
        rvHasil.layoutManager = LinearLayoutManager(requireContext())
        rvHasil.adapter = adapter

        fun cari(teks: String) {
            viewLifecycleOwner.lifecycleScope.launch {
                val hasil = dao.cari("%$teks%")
                adapter.submitList(hasil)
                txtKosong.visibility = if (hasil.isEmpty()) View.VISIBLE else View.GONE
            }
        }

        // Latihan lanjutan 2: pencarian langsung saat mengetik (menggantikan tombol Cari)
        etCari.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                cari(s.toString().trim())
            }
        })

        // Tampilkan semua kata saat tab dibuka
        cari(etCari.text.toString().trim())
    }
}
