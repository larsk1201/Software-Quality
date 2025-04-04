package com.jabberpoint;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.jabberpoint.command.CommandTest;
import com.jabberpoint.command.UndoCommandTest;
import com.jabberpoint.factory.AccessorTest;
import com.jabberpoint.memento.MementoTest;
import com.jabberpoint.ui.*;
import com.jabberpoint.util.*;

@RunWith(Suite.class)
@SuiteClasses({
    CommandTest.class,
    UndoCommandTest.class,
    AccessorTest.class,
    MementoTest.class,
    SlideTest.class,
    SlideItemTest.class,
    SlideViewerComponentTest.class,
    SlideViewerFrameTest.class,
    MenuControllerTest.class,
    AboutBoxTest.class,
    PresentationTest.class,
    StyleTest.class,
    JabberPointTest.class
})
public class JabberPointTestSuite {
}

