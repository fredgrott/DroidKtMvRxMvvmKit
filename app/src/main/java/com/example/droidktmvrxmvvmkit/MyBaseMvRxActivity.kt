/* Copyright 2018 Fred Grott

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

/*
   orig code is coprighted by

   AirBNb Apache 2.0 License
 */
package com.example.droidktmvrxmvvmkit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.airbnb.mvrx.MvRxViewModelStoreOwner
import com.airbnb.mvrx.MvRxViewModelStore

/*
  Creating my own base in case I need to add debug logs or etc
 */
abstract class BaseMvRxActivity : AppCompatActivity(), MvRxViewModelStoreOwner {

    /**
     * MvRx has its own wrapped ViewModelStore that enables improved state restoration if Android
     * kills your app and restores it in a new process.
     */
    override val mvrxViewModelStore by lazy { MvRxViewModelStore(viewModelStore) }

    override fun onCreate(savedInstanceState: Bundle?) {
        /**
         * This MUST be called before super!
         * In a new process, super.onCreate will trigger Fragment.onCreate, which could access a ViewModel.
         */
        mvrxViewModelStore.restoreViewModels(this, savedInstanceState)
        super.onCreate(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mvrxViewModelStore.saveViewModels(outState)
    }
}