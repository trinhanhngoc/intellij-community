// Copyright 2000-2018 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.intellij.ui.mac.touchbar;

import com.intellij.openapi.util.IconLoader;
import com.intellij.ui.mac.foundation.Foundation;
import java.util.HashSet;

import javax.swing.*;
import java.util.Collection;
import java.util.Random;

public class TouchbarTest {
  private static Icon ourTestIcon = IconLoader.getIcon("/modules/edit.png");

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> _createFrame());
  }

  private static void _createFrame() {
    Foundation.init();
    NST.loadLibrary();

    final TouchBar testTB = _createTestScrubberTouchbar();
    testTB.selectVisibleItemsToShow();
    NST.setTouchBar(testTB);

    final JFrame f = new JFrame();
    f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    f.setBounds(0, 0, 500, 110);
    f.setVisible(true);
  }

  private static TouchBar _createTestButtonsTouchbar() {
    final int configPopoverWidth = 143;
    final TouchBar testTB = new TouchBar("test", false);
    testTB.addButton().setText("test1").setThreadSafeAction(createPrintTextCallback("pressed test1 button"));
    testTB.addButton().setText("test2").setThreadSafeAction(createPrintTextCallback("pressed test2 button"));
    testTB.addButton().setText("test3 with suff").setThreadSafeAction(createPrintTextCallback("pressed test2 button"));
    testTB.addButton().setIcon(ourTestIcon).setThreadSafeAction(createPrintTextCallback("pressed image button"));
    testTB.addButton().setIcon(ourTestIcon).setText("IDEA very-very-very-very long suffix").setWidth(configPopoverWidth).setThreadSafeAction(createPrintTextCallback("pressed image-text button"));
    testTB.addButton().setIcon(ourTestIcon).setText("IDEA very long suffix").setWidth(configPopoverWidth + 69).setThreadSafeAction(createPrintTextCallback("pressed image-text 2 button")).setToggle(true);
    return testTB;
  }

  private static Collection<Integer> ourIndices;
  private static Collection<Integer> _makeRandomCollection(int maxIndex) {
    if (ourIndices != null)
      return ourIndices;

    final Random rnd = new Random(System.currentTimeMillis());
    final int size = rnd.nextInt(maxIndex/2);
    ourIndices = new HashSet<>();
    // System.out.println("generated test indices:");
    if (false) {
      ourIndices.add(4);
      ourIndices.add(6);
      ourIndices.add(7);
      ourIndices.add(11);
      System.out.println(ourIndices);
      return ourIndices;
    }


    for (int c = 0; c < size; ++c) {
      final int id = rnd.nextInt(maxIndex);
      ourIndices.add(id);
      // System.out.println("\t" + id);
    }

    ourIndices.remove(1);
    ourIndices.remove(2);
    return ourIndices;
  }

  private static boolean ourVisible = true;
  private static boolean ourEnabled = true;
  private static TouchBar _createTestScrubberTouchbar() {
    final TouchBar testTB = new TouchBar("test", false);
    testTB.addSpacing(true);

    final TBItemScrubber scrubber = testTB.addScrubber();
    final int size = 130;
    for (int c = 0; c < size; ++c) {
      String txt = String.format("%d[%1.2f]", c, Math.random());
      int finalC = c;
      Runnable action = () -> System.out.println("performed action of scrubber item at index " + finalC + " [thread:" + Thread.currentThread() + "]");
      if (c == 11) {
        txt = "very very long text";
      } else if (c == 1) {
        txt = "show";
        action = ()->SwingUtilities.invokeLater(()->{
          ourVisible = !ourVisible;
          NST.showScrubberItem(scrubber.myNativePeer, _makeRandomCollection(size - 1), ourVisible, false);
        });
      } else if (c == 2) {
        txt = "enable";
        action = ()->SwingUtilities.invokeLater(()->{
          ourEnabled = !ourEnabled;
          NST.enableScrubberItems(scrubber.myNativePeer, _makeRandomCollection(size - 1), ourEnabled);
        });
      }

      scrubber.addItem(ourTestIcon, txt, action);
    }

    return testTB;
  }

  private static TouchBar _createTestAllTouchbar() {
    final TouchBar testTB = new TouchBar("test", false);
    testTB.addSpacing(true);
    testTB.addButton().setText("test1").setThreadSafeAction(createPrintTextCallback("pressed test1 button"));
    testTB.addButton().setText("test2").setThreadSafeAction(createPrintTextCallback("pressed test2 button"));
    testTB.addSpacing(false);
    testTB.addButton().setIcon(ourTestIcon).setThreadSafeAction(createPrintTextCallback("pressed image button"));

    final TouchBar tapHoldTB = new TouchBar("test_popover_tap_and_hold", false);
    final TouchBar expandTB = new TouchBar("test_configs_popover_expand", false);
    final int configPopoverWidth = 143;
    testTB.addPopover(ourTestIcon, "test-popover", configPopoverWidth, expandTB, tapHoldTB);

    expandTB.addButton().setIcon(ourTestIcon).setThreadSafeAction(createPrintTextCallback("pressed popover-image button"));
    final TBItemScrubber scrubber = expandTB.addScrubber();
    for (int c = 0; c < 15; ++c) {
      String txt;
      if (c == 7)           txt = "very very long configuration name (debugging type)";
      else                  txt = String.format("r%1.2f", Math.random());
      int finalC = c;
      scrubber.addItem(ourTestIcon, txt, () -> System.out.println("JAVA: performed action of scrubber item at index " + finalC + " [thread:" + Thread.currentThread() + "]"));
    }
    expandTB.selectVisibleItemsToShow();

    tapHoldTB.addButton().setIcon(ourTestIcon).setThreadSafeAction(createPrintTextCallback("pressed tap-hold-image button"));
    tapHoldTB.selectVisibleItemsToShow();

    return testTB;
  }

  private static Runnable createPrintTextCallback(String text) {
    return ()-> System.out.println(text + " [thread:" + Thread.currentThread() + "]");
  }
}

