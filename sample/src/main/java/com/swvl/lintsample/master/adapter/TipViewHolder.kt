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

package com.swvl.lintsample.master.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.swvl.lintsample.R
import com.swvl.lintsample.databinding.ItemTipBinding

class TipViewHolder(private val binding: ItemTipBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(tip: Tip, onItemClickListener: (Int) -> Unit) {
        binding.btnId.apply {
            text = context.getString(R.string.tip_title, tip.ordinal + 1)
            setOnClickListener {
                onItemClickListener.invoke(tip.ordinal)
            }
        }
    }

    companion object {
        fun create(container: ViewGroup): TipViewHolder {
            val binding = ItemTipBinding.inflate(
                LayoutInflater.from(container.context),
                container,
                false
            )
            return TipViewHolder(binding)
        }
    }
}
