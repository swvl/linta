/**
 * Copyright 2022 Swvl
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.swvl.lintsample.detail

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.swvl.lintsample.R
import com.swvl.lintsample.databinding.FragmentDetailBinding
import com.swvl.lintsample.master.adapter.Tip

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding

    private val args by navArgs<DetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayTip()
        initClickListeners()
    }

    private fun displayTip() {
        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            getString(R.string.tip_title, args.tipId + 1)
        binding.tvTip.text = getString(Tip.values()[args.tipId].stringResId)
    }

    private fun initClickListeners() {
        binding.apply {
            btnPrimaryColor.setOnClickListener {
                val color = Color.parseColor(COLOR_PRIMARY)
                tvTip.setTextColor(color)
                ivCode.setBackgroundColor(color)
            }

            btnSecondaryColor.setOnClickListener {
                val color = Color.rgb(3, 218, 197)
                tvTip.setTextColor(color)
                ivCode.setBackgroundColor(color)
            }
        }
    }

    companion object {
        private const val COLOR_PRIMARY = "#FF6200EE"
    }
}
