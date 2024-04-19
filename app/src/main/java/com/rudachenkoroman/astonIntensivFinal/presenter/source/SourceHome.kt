package com.rudachenkoroman.astonIntensivFinal.presenter.source
import com.rudachenkoroman.astonIntensivFinal.model.source.SourceResponse

interface SourceHome {
    interface Presenter {
        fun requestAll()
        fun onSuccess(sourceResponse: SourceResponse)
        fun onError(message: String)
        fun onComplete()
    }
}