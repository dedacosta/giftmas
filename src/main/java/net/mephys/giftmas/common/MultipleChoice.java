package net.mephys.giftmas.common;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MultipleChoice {
    private final List<Integer> vec;
    private static final SecureRandom RAND = new SecureRandom();

    public MultipleChoice(int n) {
        vec = new ArrayList<>();
        for (int i = 0; i < n; i++) vec.add(i);
        if(n>1) {
            do {
                Collections.shuffle(vec, RAND);
            } while (!isValid());
        }
    }

    private boolean isValid() {
        for (int i = 0; i < vec.size(); i++) {
            if (vec.get(i) == i) return false;
        }
        return true;
    }

    public int get(int i) {
        if (i < 0 || i >= vec.size()) {
            throw new IllegalArgumentException("Index out of range");
        }
        return vec.get(i);
    }

    @Override
    public String toString() {
        return String.join(",", vec.stream().map(String::valueOf).toList());
    }
}
