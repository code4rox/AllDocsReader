/* ====================================================================
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
==================================================================== */

package com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.formula.function;

/**
 * Implementation of Excel function SUMXMY2()<p/>
 *
 * Calculates the sum of squares of differences between two arrays of the same size.<br/>
 * <b>Syntax</b>:<br/>
 * <b>SUMXMY2</b>(<b>arrayX</b>, <b>arrayY</b>)<p/>
 *
 * result = &Sigma;<sub>i: 0..n</sub>(x<sub>i</sub>-y<sub>i</sub>)<sup>2</sup>
 *
 * @author Amol S. Deshmukh &lt; amolweb at ya hoo dot com &gt;
 */
public final class Sumxmy2 extends XYNumericFunction {

	private static final Accumulator XMinusYSquaredAccumulator = new Accumulator() {
		public double accumulate(double x, double y) {
			double xmy = x - y;
			return xmy * xmy;
		}
	};

	protected Accumulator createAccumulator() {
		return XMinusYSquaredAccumulator;
	}
}
