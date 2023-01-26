/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016-2023 Objectionary.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.eolang.maven.footprint;

import java.io.IOException;
import java.nio.file.Path;
import org.cactoos.Scalar;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Tests for Cached.
 * @since 1.0
 */
final class FtCachedTest {

    /**
     * Default content.
     */
    private static final Scalar<String> CONTENT = () -> "content";

    @Test
    void loadsContentOfCachedFile(@TempDir final Path temp) throws Exception {
        final Path target = temp.resolve("target");
        final Path parsed = temp.resolve("parsed");
        final Footprint cached = new FtCached("abcde123", parsed, new FtDefault(target));
        final String program = "org.eolang.txt.format";
        cached.save(program, "xmir", FtCachedTest.CONTENT);
        MatcherAssert.assertThat(
            cached.load(program, "xmir"),
            Matchers.equalTo(FtCachedTest.CONTENT.value())
        );
    }

    @Test
    void returnsListOfSavedFilesFromDelegate(@TempDir final Path temp) throws IOException {
        final Path target = temp.resolve("target");
        final Footprint footprint = new FtCached(
            "abcde123",
            temp.resolve("parsed"),
            new FtDefault(target)
        );
        footprint.save("prog", "xmir", FtCachedTest.CONTENT);
        MatcherAssert.assertThat(
            footprint.list("xmir"),
            Matchers.hasItem(target.resolve("prog.xmir"))
        );
    }
}