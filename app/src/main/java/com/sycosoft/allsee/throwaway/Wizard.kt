package com.sycosoft.allsee.throwaway

data class Wizard(
    val id: String,
    val firstName: String?,
    val lastName: String?,
    val elixirs: List<WizardElixir>
)

data class WizardElixir(
    val id: String,
    val name: String,
)