package com.zhalz.friendzy.ui.modify

import androidx.lifecycle.viewModelScope
import com.crocodic.core.api.ApiObserver
import com.zhalz.friendzy.base.BaseViewModel
import com.zhalz.friendzy.data.response.LoginResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ModifyViewModel @Inject constructor(): BaseViewModel() {

    fun update(id: Int?, name: String?, school: String, desc: String?, photo: File?, isUser: Boolean ) {
        viewModelScope.launch {
            val fileBody = photo?.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            val filePart =
                fileBody?.let { MultipartBody.Part.createFormData("photo", photo.name, it) }

            ApiObserver.run({ apiService.update(id, name, school, desc, filePart) }, false,
                object : ApiObserver.ModelResponseListener<LoginResponse> {
                    override suspend fun onSuccess(response: LoginResponse) {
                        super.onSuccess(response)
                        if (isUser) response.data?.let { userRepositoryImpl.insert(it) }
                    }
                })
        }
    }

    fun updateNoPhoto(id: Int?, name: String?, school: String, desc: String?, isUser: Boolean) {
        viewModelScope.launch {
            ApiObserver.run({ apiService.updateNoPhoto(id, name, school, desc) }, false,
                object : ApiObserver.ModelResponseListener<LoginResponse> {
                    override suspend fun onSuccess(response: LoginResponse) {
                        super.onSuccess(response)
                        if (isUser) response.data?.let { userRepositoryImpl.insert(it) }
                    }
                })
        }
    }

}
