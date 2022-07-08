/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016-2022 Yegor Bugayenko
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

package EOorg.EOeolang;

import org.eolang.AtComposite;
import org.eolang.AtFree;
import org.eolang.Attr;
import org.eolang.Data;
import org.eolang.ExAbstract;
import org.eolang.Param;
import org.eolang.PhDefault;
import org.eolang.PhWith;
import org.eolang.Phi;
import org.eolang.XmirObject;

import java.math.BigInteger;

/**
 * ERROR.
 *
 * @since 0.22
 */
@XmirObject(oname = "error")
public class EOerror extends PhDefault {

    /**
     * Ctor.
     * @param sigma Sigma
     */
    public EOerror(final Phi sigma) {
        super(sigma);
        this.add("msg", new AtFree());
        this.add(
            "φ",
            new AtComposite(
                this,
                rho -> {
                    throw new ExError(sigma, rho);
                }
            )
        );
    }

    /**
     * Phi constructed with error.
     * @since 1.0
     */
    public static class PhWithError implements Phi {

        /**
         * Constructed phi.
         */
        private final Phi phi;

        /**
         * Ctor.
         * @param msg Error message
         */
        public PhWithError(String msg) {
            this.phi = new PhWith(
                new EOerror(Phi.Φ),
                "msg",
                new Data.ToPhi(
                    msg
                )
            );
        }

        /**
         * Ctor.
         * @param format Message format string
         * @param params Parameters
         */
        public PhWithError(String format, Object... params) {
            this(
                String.format(
                    format,
                    params
                )
            );
        }

        @Override
        public Phi copy() {
            return phi.copy();
        }

        @Override
        public void move(Phi rho) {
            phi.move(rho);
        }

        @Override
        public Attr attr(int pos) {
            return phi.attr(pos);
        }

        @Override
        public Attr attr(String name) {
            return phi.attr(name);
        }

        @Override
        public String φTerm() {
            return phi.φTerm();
        }
    }

    /**
     * When exception happens.
     * @since 0.19
     */
    public static class ExError extends ExAbstract {
        /**
         * Serialization identifier.
         */
        private static final long serialVersionUID = 1735493012609760997L;
        /**
         * Sigma.
         */
        public final Phi sigma;
        /**
         * Exception.
         */
        public final Phi exception;

        /**
         * Ctor.
         * @param sgm Sigma
         * @param exp Exception
         */
        ExError(final Phi sgm, final Phi exp) {
            super();
            this.sigma = sgm;
            this.exception = exp;
        }
    }

}
