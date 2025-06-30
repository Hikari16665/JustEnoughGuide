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

package com.balugaq.jeg.implementation.items;

import com.balugaq.jeg.implementation.JustEnoughGuide;
import com.balugaq.jeg.utils.Models;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemsSetup {
    public static final RecipeCompleteGuide RECIPE_COMPLETE_GUIDE;

    static {
        ItemStack craftingTable = new ItemStack(Material.CRAFTING_TABLE);
        ItemStack book = new ItemStack(Material.BOOK);

        RECIPE_COMPLETE_GUIDE = new RecipeCompleteGuide(
                GroupSetup.jegItemsGroup,
                Models.RECIPE_COMPLETE_GUIDE,
                RecipeType.ENHANCED_CRAFTING_TABLE,
                //@formatter:off
                new ItemStack[] {
                        craftingTable, craftingTable, craftingTable,
                        craftingTable, book,          craftingTable,
                        craftingTable, craftingTable, craftingTable
                }
                //@formatter:on
        );
    }

    public static void setup() {
        RECIPE_COMPLETE_GUIDE.register(JustEnoughGuide.getInstance());
    }
}
