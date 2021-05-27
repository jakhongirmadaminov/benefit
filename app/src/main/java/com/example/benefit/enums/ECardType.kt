package com.example.benefit.enums

enum class ECardType {
    ZOOM,
    SUPREME;

    companion object {

        fun ECardType.isZoom(): Boolean {
            return this == ZOOM
        }

    }
}
