package com.codedisaster.steamworks;

import java.util.Arrays;

/**
 * Describes a DualSense adaptive trigger effect, mirroring ScePadTriggerEffectParam, for
 * {@link SteamInput#setDualSenseTriggerEffect(SteamInputHandle, SteamInputDualSenseTriggerEffect)}.
 * <p>
 * Each setter configures one trigger and marks it to be applied. Triggers that no setter touched
 * keep their current effect. Positions run from 0 (released) to 9 (fully pressed), strengths and
 * amplitudes from 0 to 8, where 0 acts like {@link Mode#Off}, and frequencies from 0 to 255 Hz.
 * Values outside these ranges are passed through as unsigned bytes, and the controller decides
 * how to treat them.
 * <p>
 * An instance can be reused: call {@link #reset()} and configure it again.
 */
@SuppressWarnings("unused")
public class SteamInputDualSenseTriggerEffect {

	public enum Trigger {
		L2,
		R2
	}

	public enum Mode {
		Off,
		Feedback,
		Weapon,
		Vibration,
		MultiplePositionFeedback,
		SlopeFeedback,
		MultiplePositionVibration
	}

	public static final int CONTROL_POINT_COUNT = 10;

	/**
	 * Size of the command data union in ScePadTriggerEffectCommand.
	 */
	static final int PARAM_SIZE = 48;

	int triggerMask;
	final int[] modes = new int[2];
	final byte[] params = new byte[2 * PARAM_SIZE];

	/**
	 * Clears every trigger, so the effect applies to none.
	 */
	public SteamInputDualSenseTriggerEffect reset() {
		triggerMask = 0;
		modes[0] = modes[1] = 0;
		Arrays.fill(params, (byte) 0);
		return this;
	}

	/**
	 * Stops the trigger's effect.
	 */
	public SteamInputDualSenseTriggerEffect setOff(Trigger trigger) {
		begin(trigger, Mode.Off);
		return this;
	}

	/**
	 * The trigger resists with the given strength from the given position on.
	 */
	public SteamInputDualSenseTriggerEffect setFeedback(Trigger trigger, int position, int strength) {
		int offset = begin(trigger, Mode.Feedback);
		params[offset] = (byte) position;
		params[offset + 1] = (byte) strength;
		return this;
	}

	/**
	 * The trigger resists between two positions, then gives way, like a gun trigger.
	 *
	 * @param startPosition 2 to 7
	 * @param endPosition   startPosition + 1 to 8
	 */
	public SteamInputDualSenseTriggerEffect setWeapon(Trigger trigger, int startPosition, int endPosition,
													  int strength) {
		int offset = begin(trigger, Mode.Weapon);
		params[offset] = (byte) startPosition;
		params[offset + 1] = (byte) endPosition;
		params[offset + 2] = (byte) strength;
		return this;
	}

	/**
	 * The trigger vibrates from the given position on.
	 */
	public SteamInputDualSenseTriggerEffect setVibration(Trigger trigger, int position, int amplitude, int frequency) {
		int offset = begin(trigger, Mode.Vibration);
		params[offset] = (byte) position;
		params[offset + 1] = (byte) amplitude;
		params[offset + 2] = (byte) frequency;
		return this;
	}

	/**
	 * The trigger resists with a separate strength at each of the {@link #CONTROL_POINT_COUNT} positions.
	 */
	public SteamInputDualSenseTriggerEffect setMultiplePositionFeedback(Trigger trigger, int[] strengths) {
		checkControlPoints(strengths);
		int offset = begin(trigger, Mode.MultiplePositionFeedback);
		for (int i = 0; i < CONTROL_POINT_COUNT; i++) {
			params[offset + i] = (byte) strengths[i];
		}
		return this;
	}

	/**
	 * The trigger's resistance changes linearly between two positions.
	 *
	 * @param startStrength 1 to 8
	 * @param endStrength   1 to 8
	 */
	public SteamInputDualSenseTriggerEffect setSlopeFeedback(Trigger trigger, int startPosition, int endPosition,
															 int startStrength, int endStrength) {
		int offset = begin(trigger, Mode.SlopeFeedback);
		params[offset] = (byte) startPosition;
		params[offset + 1] = (byte) endPosition;
		params[offset + 2] = (byte) startStrength;
		params[offset + 3] = (byte) endStrength;
		return this;
	}

	/**
	 * The trigger vibrates with a separate amplitude at each of the {@link #CONTROL_POINT_COUNT} positions.
	 */
	public SteamInputDualSenseTriggerEffect setMultiplePositionVibration(Trigger trigger, int frequency,
																		 int[] amplitudes) {
		checkControlPoints(amplitudes);
		int offset = begin(trigger, Mode.MultiplePositionVibration);
		params[offset] = (byte) frequency;
		for (int i = 0; i < CONTROL_POINT_COUNT; i++) {
			params[offset + 1 + i] = (byte) amplitudes[i];
		}
		return this;
	}

	public boolean isSet(Trigger trigger) {
		return (triggerMask & (1 << trigger.ordinal())) != 0;
	}

	public Mode getMode(Trigger trigger) {
		return Mode.values()[modes[trigger.ordinal()]];
	}

	/**
	 * Marks the trigger, sets its mode, clears its parameters, and returns their offset.
	 */
	private int begin(Trigger trigger, Mode mode) {
		int index = trigger.ordinal();
		triggerMask |= 1 << index;
		modes[index] = mode.ordinal();
		int offset = index * PARAM_SIZE;
		Arrays.fill(params, offset, offset + PARAM_SIZE, (byte) 0);
		return offset;
	}

	private static void checkControlPoints(int[] values) {
		if (values.length < CONTROL_POINT_COUNT) {
			throw new IllegalArgumentException("Array size must be at least CONTROL_POINT_COUNT");
		}
	}

}
