/*
 * Copyright (c) 2024-2025 balugaq
 *
 * This file is part of JustEnoughGuide, available under MIT license.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * - The above copyright notice and this permission notice shall be included in
 *   all copies or substantial portions of the Software.
 * - The author's name (balugaq or 大香蕉) and project name (JustEnoughGuide or JEG) shall not be
 *   removed or altered from any source distribution or documentation.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package com.balugaq.jeg.utils.formatter;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author balugaq
 * @since 1.6
 */
@SuppressWarnings("unused")
@Getter
@ToString
public abstract class Format {
    @ToString.Include
    public final Map<Integer, Character> mapping = new HashMap<>();
    @ToString.Exclude
    public final Map<Character, List<Integer>> cached = new HashMap<>();
    @ToString.Include
    @Setter
    public int size = 54;

    public abstract void loadMapping();

    @ApiStatus.Obsolete
    public void loadMapping(@NotNull List<String> format) {
        int index = -1;
        for (var string : format) {
            for (var c : string.toCharArray()) {
                index++;
                if (c != ' ') {
                    mapping.put(index, c);
                }
            }
        }
    }

    @ApiStatus.Obsolete
    public List<Integer> getChars(@NotNull String s) {
        return getChars(s.toCharArray()[0]);
    }

    @ApiStatus.Obsolete
    public List<Integer> getChars(char c) {
        if (cached.containsKey(c)) {
            return cached.get(c);
        }

        List<Integer> list = new ArrayList<>();
        for (var entry : mapping.entrySet()) {
            if (entry.getValue() == c) {
                list.add(entry.getKey());
            }
        }

        cached.put(c, list);
        return list;
    }
}
