// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.intellij.ui.layout

import com.intellij.testFramework.fixtures.BasePlatformTestCase
import javax.swing.JRadioButton

/**
 * @author yole
 */
class UiDslBindingsTest : BasePlatformTestCase() {
  private var booleanValue = false
  private var intValue = 0

  fun testRadioButtonWithBooleanBinding() {
    booleanValue = false
    val dialogPanel = panel {
      row {
        buttonGroup {
          radioButton("Foo", ::booleanValue)
          radioButton("Bar")
        }
      }
    }
    dialogPanel.reset()
    val radioButtons = dialogPanel.components.filterIsInstance<JRadioButton>()
    assertTrue(radioButtons[1].isSelected)
    radioButtons[0].isSelected = true
    assertFalse(radioButtons[1].isSelected)
    dialogPanel.apply()
    assertTrue(booleanValue)
  }

  fun testRadioButtonWithIntBinding() {
    intValue = 2
    val dialogPanel = panel {
      row {
        buttonGroup(::intValue) {
          radioButton("Foo", 0)
          radioButton("Bar", 1)
          radioButton("Baz", 2)
        }
      }
    }

    dialogPanel.reset()
    val radioButtons = dialogPanel.components.filterIsInstance<JRadioButton>()
    assertTrue(radioButtons[2].isSelected)
    radioButtons[1].isSelected = true
    assertFalse(radioButtons[2].isSelected)
    dialogPanel.apply()
    assertEquals(1, intValue)
  }

  fun testRadioButtonCellWithIntBinding() {
    intValue = 2
    val dialogPanel = panel {
      row {
        cell {
          buttonGroup(::intValue) {
            radioButton("Foo", 0)
            radioButton("Bar", 1)
            radioButton("Baz", 2)
          }
        }
      }
    }

    dialogPanel.reset()
    val radioButtons = dialogPanel.components.filterIsInstance<JRadioButton>()
    assertTrue(radioButtons[2].isSelected)
    radioButtons[1].isSelected = true
    assertFalse(radioButtons[2].isSelected)
    dialogPanel.apply()
    assertEquals(1, intValue)
  }
}
