package com.konradpekala.blefik.domain.model

import com.konradpekala.blefik.utils.schedulers.ValueToUpdate

data class UserUpdateRequest(val value: Any, val valueToUpdate: ValueToUpdate)