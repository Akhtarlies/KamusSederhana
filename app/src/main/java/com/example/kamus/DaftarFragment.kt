package com.example.kamus

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class DaftarFragment : Fragment(R.layout.fragment_daftar) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dao = KamusDatabase.getInstance(requireContext()).kataDao()
        val txtJumlah = view.findViewById<TextView>(R.id.txtJumlah)
        val rvDaftar = view.findViewById<RecyclerView>(R.id.rvDaftar)

        val adapter = KataAdapter()
        rvDaftar.layoutManager = LinearLayoutManager(requireContext())
        rvDaftar.adapter = adapter

        // Daftar ikut berubah otomatis saat data ditambah/dihapus
        viewLifecycleOwner.lifecycleScope.launch {
            dao.ambilSemua().collect { daftar ->
                adapter.submitList(daftar)
                txtJumlah.text = "Jumlah kata: ${daftar.size}"
            }
        }

        // Latihan lanjutan 1: geser kiri/kanan untuk menghapus kata
        val geser = object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                rv: RecyclerView,
                vh: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = false

            override fun onSwiped(vh: RecyclerView.ViewHolder, direction: Int) {
                val posisi = vh.bindingAdapterPosition
                if (posisi == RecyclerView.NO_POSITION) return
                val kata = adapter.currentList[posisi]
                viewLifecycleOwner.lifecycleScope.launch {
                    dao.hapus(kata)
                    Toast.makeText(requireContext(), "${kata.indonesia} dihapus", Toast.LENGTH_SHORT).show()
                }
            }
        }
        ItemTouchHelper(geser).attachToRecyclerView(rvDaftar)
    }
}
