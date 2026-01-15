package com.example.endassigment.model;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PageTest {

    @Test
    void testPageStructure() {
        List<Element> questions = new ArrayList<>();
        questions.add(new BooleanElement());
        questions.add(new RadiogroupElement());

        Page page = new Page(30, questions);

        assertEquals(30, page.getTimeLimit());
        assertEquals(2, page.getElements().size(), "Page must have 2 questions");
        assertNotNull(page.getElements(), "Element list must be different than null");
    }
}