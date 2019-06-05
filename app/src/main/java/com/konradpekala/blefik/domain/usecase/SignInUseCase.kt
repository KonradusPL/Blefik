package com.konradpekala.blefik.domain.usecase

import com.konradpekala.blefik.data.model.request.LoginRequest
import com.konradpekala.blefik.data.repo.LoginRepository
import io.reactivex.Scheduler
import io.reactivex.Single

class SignInUseCase(tScheduler: Scheduler,
                    pTScheduler: Scheduler,
                    private val loginRepository: LoginRepository
): SingleUseCase<LoginRequest, String>(tScheduler, pTScheduler) {

    override fun buildUseCaseSingle(p: LoginRequest?): Single<String> {
        return loginRepository.signIn(p!!.email,p.password)
            .flatMap { id: String -> loginRepository.getUserNick(id) }
    }
}