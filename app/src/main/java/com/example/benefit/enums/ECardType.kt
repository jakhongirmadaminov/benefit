package com.example.benefit.enums

enum class ECardType {
    ZOOM,
    GENERIC,
    SUPREME;

    companion object {

        fun ECardType.isZoom(): Boolean {
            return this == ZOOM
        }

    }
}
