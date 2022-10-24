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

import androidx.annotation.StringRes
import com.swvl.lintsample.R

enum class Tip(@StringRes val stringResId: Int) {

    TIP_1(R.string.tip_1),
    TIP_2(R.string.tip_2),
    TIP_3(R.string.tip_3),
    TIP_4(R.string.tip_4)
}
