package com.example.techshop4;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class SpyTest {
    @Spy
    private List<Integer> spiedList = new ArrayList<>();

    @Test
    public void spyTest() {
        spiedList.add(1);
        spiedList.add(2);
        spiedList.add(3);

        Mockito.verify(spiedList).add(1);
        Mockito.verify(spiedList).add(2);
        Mockito.verify(spiedList).add(3);

        Assertions.assertEquals(3, spiedList.size());

        Mockito.doReturn(100).when(spiedList).size();
        Assertions.assertEquals(100, spiedList.size());
    }
}