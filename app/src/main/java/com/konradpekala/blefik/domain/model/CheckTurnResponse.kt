package com.konradpekala.blefik.domain.model

import com.konradpekala.blefik.data.model.Player

data class CheckTurnResponse (val checkBidResponse: CheckBidResponse,
                              val playerRemoved: Player? = null,
                              val isSomeoneRemoved: Boolean,
                              val doesGameNeedToEnd: Boolean)