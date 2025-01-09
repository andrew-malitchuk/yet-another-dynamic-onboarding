package dev.yado.lib.source.state

/**
 * Represents the internal states of the YADO (Yet Another Dynamic Onboarding) system.
 *
 * This sealed class defines the various states that the system can transition through
 * during the dynamic onboarding process, enabling precise control and tracking of the onboarding flow.
 */
sealed class YadoInternalState {
    /**
     * Represents the initial state of the onboarding process.
     * This state is typically used when the onboarding process is about to start.
     */
    object Init : YadoInternalState()

    /**
     * Represents the state where the onboarding process is actively in progress.
     * This can indicate that steps are being executed or the user is interacting with the onboarding.
     */
    object InProgress : YadoInternalState()

    /**
     * Represents the state where the onboarding process is transitioning to the next step.
     * This state signals that a step has been completed and the next one is about to begin.
     */
    object Next : YadoInternalState()

    /**
     * Represents an idle state where no active transition or operation is occurring.
     * This state might be used when the onboarding process is paused or waiting for user interaction.
     */
    object Idle : YadoInternalState()
}
