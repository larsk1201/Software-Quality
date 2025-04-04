package com.jabberpoint.memento;

import static org.junit.Assert.*;
import org.junit.Test;

import com.jabberpoint.ui.Slide;
import com.jabberpoint.util.Presentation;
import java.util.ArrayList;
import java.util.Stack;

public class MementoTest {

    @Test
    public void mementoConstructorSetsTitleAndSlides() {
        String title = "Test Title";
        ArrayList<Slide> slides = new ArrayList<>();
        slides.add(new Slide());
        Memento memento = new Memento(title, slides);
        assertEquals(title, memento.getSavedTitle());
        assertEquals(slides, memento.getSavedSlides());
    }
    
    @Test
    public void setSavedTitleChangesTheTitleInMemento() {
        Memento memento = new Memento("Old Title", new ArrayList<>());
        memento.setSavedTitle("New Title");
        assertEquals("New Title", memento.getSavedTitle());
    }
    
    @Test
    public void setSavedSlidesChangesTheSlidesInMemento() {
        Memento memento = new Memento("Title", new ArrayList<>());
        ArrayList<Slide> newSlides = new ArrayList<>();
        newSlides.add(new Slide());
        memento.setSavedSlides(newSlides);
        assertEquals(newSlides, memento.getSavedSlides());
    }
    
    @Test
    public void presentationCaretakerSaveAndLoadRestoresPresentationState() {
        PresentationCaretaker caretaker = new PresentationCaretaker();
        Presentation presentation = new Presentation();
        presentation.setTitle("Original Title");
        caretaker.save(presentation);
        presentation.setTitle("Modified Title");
        caretaker.load(presentation);
        assertEquals("Original Title", presentation.getTitle());
    }
    
    @Test
    public void presentationCaretakerSupportsMultipleUndos() {
        PresentationCaretaker caretaker = new PresentationCaretaker();
        Presentation presentation = new Presentation();
        presentation.setTitle("State 1");
        caretaker.save(presentation);
        presentation.setTitle("State 2");
        caretaker.save(presentation);
        presentation.setTitle("State 3");
        caretaker.save(presentation);
        caretaker.load(presentation);
        assertEquals("State 2", presentation.getTitle());
        caretaker.load(presentation);
        assertEquals("State 1", presentation.getTitle());
    }
    
    @Test
    public void presentationCaretakerLoadWithEmptyHistoryDoesNotChangePresentation() {
        PresentationCaretaker caretaker = new PresentationCaretaker();
        Presentation presentation = new Presentation();
        presentation.setTitle("Original Title");
        caretaker.load(presentation);
        assertEquals("Original Title", presentation.getTitle());
    }
    
    @Test
    public void getAndSetHistoryChangesTheHistoryStackInCaretaker() {
        PresentationCaretaker caretaker = new PresentationCaretaker();
        Stack<Memento> history = new Stack<>();
        Memento memento = new Memento("Test", new ArrayList<>());
        history.push(memento);
        caretaker.setHistory(history);
        Stack<Memento> retrievedHistory = caretaker.getHistory();
        assertSame(history, retrievedHistory);
        assertEquals(1, retrievedHistory.size());
        assertEquals("Test", retrievedHistory.peek().getSavedTitle());
    }
    
    @Test
    public void clearHistoryRemovesAllMementosFromHistory() {
        PresentationCaretaker caretaker = new PresentationCaretaker();
        Presentation presentation = new Presentation();
        
        caretaker.save(presentation);
        caretaker.save(presentation);
        caretaker.save(presentation);
        
        assertFalse(caretaker.getHistory().isEmpty());
        assertEquals(3, caretaker.getHistory().size());
        
        caretaker.clearHistory();
        
        assertTrue(caretaker.getHistory().isEmpty());
        assertEquals(0, caretaker.getHistory().size());
    }
}

