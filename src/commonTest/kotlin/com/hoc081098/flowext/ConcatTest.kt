package com.hoc081098.flowext

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlin.test.Test

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class ConcatTest {
  @Test
  fun testConcat_shouldEmitValuesFromMultipleFlows() = suspendTest {
    concat(
      flow1 = flowOf(1, 2, 3),
      flow2 = flowOf(4, 5, 6),
    ).test((1..6).map { Event.Value(it) } + Event.Complete)

    concat(
      flow1 = flowOf(1, 2, 3),
      flow2 = flowOf(4, 5, 6),
      flow3 = flowOf(7, 8, 9),
    ).test((1..9).map { Event.Value(it) } + Event.Complete)

    concat(
      flow1 = flowOf(1, 2, 3),
      flow2 = flowOf(4, 5, 6),
      flow3 = flowOf(7, 8, 9),
      flow4 = flowOf(10, 11, 12),
    ).test((1..12).map { Event.Value(it) } + Event.Complete)

    concat(
      flow1 = flowOf(1, 2, 3),
      flow2 = flowOf(4, 5, 6),
      flow3 = flowOf(7, 8, 9),
      flow4 = flowOf(10, 11, 12),
      flow5 = flowOf(13, 14, 15),
    ).test((1..15).map { Event.Value(it) } + Event.Complete)

    concat(
      flowOf(1, 2, 3),
      flowOf(4, 5, 6),
      flowOf(7, 8, 9),
      flowOf(10, 11, 12),
      flowOf(13, 14, 15),
      flowOf(16, 17, 18),
    ).test((1..18).map { Event.Value(it) } + Event.Complete)
  }

  @Test
  fun testConcat_shouldConcatTheSameColdFlowMultipleTimes() = suspendTest {
    val flow = flowOf(1, 2, 3)
    val events = (1..3).map { Event.Value(it) }

    concat(
      flow1 = flow,
      flow2 = flow,
    ).test(events * 2 + Event.Complete)

    concat(
      flow1 = flow,
      flow2 = flow,
      flow3 = flow,
    ).test(events * 3 + Event.Complete)

    concat(
      flow1 = flow,
      flow2 = flow,
      flow3 = flow,
      flow4 = flow,
    ).test(events * 4 + Event.Complete)

    concat(
      flow1 = flow,
      flow2 = flow,
      flow3 = flow,
      flow4 = flow,
      flow5 = flow,
    ).test(events * 5 + Event.Complete)

    concat(
      flow,
      flow,
      flow,
      flow,
      flow,
      flow,
    ).test(events * 6 + Event.Complete)
  }
}

private operator fun <T> Iterable<T>.times(times: Int): List<T> = (0 until times).flatMap { this }
