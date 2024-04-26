package com.rudachenkoroman.astonIntensivFinal.presentation.presenter.source
import com.rudachenkoroman.astonIntensivFinal.domain.model.source.SourceResponse

interface SourceHome {
    interface Presenter {
        fun requestAll()
        fun onSuccess(sourceResponse: SourceResponse)
        fun onError(message: String)
        fun onComplete()
    }
}