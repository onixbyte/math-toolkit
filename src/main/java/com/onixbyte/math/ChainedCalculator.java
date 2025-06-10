/*
 * Copyright (c) 2024-2025 OnixByte
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.onixbyte.math;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * The {@code ChainedCalculator} class provides a convenient way to perform chained high-precision
 * calculations using {@link BigDecimal}. It allows users to perform mathematical operations such
 * as addition, subtraction, multiplication, and division with customisable precision and scale.
 * By using this utility class, developers can achieve accurate results and avoid precision loss
 * in their calculations.
 * <p>
 * <b>Usage:</b>
 * <pre>{@code
 * // Perform addition: 3 + 4
 * BigDecimal result1 = ChainedCalculator.startWith(3)
 *                                     .add(4)
 *                                     .getValue();
 * // Perform subtraction: 4 - 2
 * BigDecimal result2 = ChainedCalculator.startWith(4)
 *                                     .subtract(2)
 *                                     .getValue();
 * // Perform multiplication: 3 * 6
 * BigDecimal result3 = ChainedCalculator.startWith(3)
 *                                     .multiply(6)
 *                                     .getValue();
 * // Perform division: 6 ÷ 2
 * BigDecimal result4 = ChainedCalculator.startWith(6)
 *                                     .divide(2)
 *                                     .getValue();
 * // Perform division with specified scale: 13 ÷ 7 with a scale of 2
 * BigDecimal result5 = ChainedCalculator.startWith(13)
 *                                     .divideWithScale(7, 2)
 *                                     .getValue();
 * // Get int, long, or double results
 * int intResult = ChainedCalculator.startWith(3)
 *                               .add(4)
 *                               .getInteger();
 * long longResult = ChainedCalculator.startWith(4)
 *                                 .subtract(2)
 *                                 .getLong();
 * double doubleResult = ChainedCalculator.startWith(6)
 *                                     .divide(2)
 *                                     .getDouble();
 * // Get BigDecimal result with specified scale
 * BigDecimal result6 = ChainedCalculator.startWith(13)
 *                                     .divide(7)
 *                                     .getValue(2);
 * }</pre>
 * The above expressions perform various mathematical calculations using the
 * {@code ChainedCalculator} class.
 * <p>
 * <b>Note:</b>
 * The {@code ChainedCalculator} class internally uses {@link BigDecimal} to handle high-precision
 * calculations. It is important to note that {@link BigDecimal} operations can be memory-intensive
 * and may have performance implications for extremely large numbers or complex calculations.
 *
 * @author sunzsh
 * @author zihluwang
 * @author siujamo
 * @version 3.0.0
 * @see BigDecimal
 */
public class ChainedCalculator {

    /**
     * The current value.
     */
    private BigDecimal value;

    /**
     * Creates a {@code ChainedCalculator} instance with the specified initial value.
     *
     * @param value the initial value for the calculation
     */
    private ChainedCalculator(Number value) {
        this.value = convertBigDecimal(value, null);
    }

    /**
     * Starts a chained calculation with the specified initial value.
     *
     * @param value the initial value for the calculation
     * @return a {@code ChainedCalculator} instance for performing chained calculations
     */
    public static ChainedCalculator startWith(Number value) {
        return new ChainedCalculator(value);
    }

    /**
     * Adds the specified value to the current value.
     *
     * @param other the value to be added
     * @return a {@code ChainedCalculator} instance with the updated value
     */
    public ChainedCalculator add(Number other) {
        return operator(BigDecimal::add, other);
    }

    /**
     * Adds the specified value to the current value with a specified scale before the operation.
     *
     * @param other              the value to be added
     * @param beforeOperateScale the scale to be applied before the operation
     * @return a {@code ChainedCalculator} instance with the updated value
     */
    public ChainedCalculator add(Number other, Integer beforeOperateScale) {
        return operator(BigDecimal::add, other, beforeOperateScale);
    }

    /**
     * Subtracts the specified value from the current value.
     *
     * @param other the value to be subtracted
     * @return a {@code ChainedCalculator} instance with the updated value
     */
    public ChainedCalculator subtract(Number other) {
        return operator(BigDecimal::subtract, other);
    }

    /**
     * Subtracts the specified value from the current value with a specified scale before
     * the operation.
     *
     * @param other              the value to be subtracted
     * @param beforeOperateScale the scale to be applied before the operation
     * @return a {@code ChainedCalculator} instance with the updated value
     */
    public ChainedCalculator subtract(Number other, Integer beforeOperateScale) {
        return operator(BigDecimal::subtract, other, beforeOperateScale);
    }

    /**
     * Multiplies the current value by the specified value.
     *
     * @param other the value to be multiplied by
     * @return a {@code ChainedCalculator} instance with the updated value
     */
    public ChainedCalculator multiply(Number other) {
        return operator(BigDecimal::multiply, other);
    }

    /**
     * Multiplies the current value by the specified value with a specified scale before
     * the operation.
     *
     * @param other              the value to be multiplied by
     * @param beforeOperateScale the scale to be applied before the operation
     * @return a {@code ChainedCalculator} instance with the updated value
     */
    public ChainedCalculator multiply(Number other, Integer beforeOperateScale) {
        return operator(BigDecimal::multiply, other, beforeOperateScale);
    }

    /**
     * Divides the current value by the specified value.
     *
     * @param other the value to divide by
     * @return a {@code ChainedCalculator} instance with the updated value
     */
    public ChainedCalculator divide(Number other) {
        return operator(BigDecimal::divide, other);
    }

    /**
     * Divides the current value by the specified value with a specified scale before the operation.
     *
     * @param other              the value to divide by
     * @param beforeOperateScale the scale to be applied before the operation
     * @return a {@code ChainedCalculator} instance with the updated value
     */
    public ChainedCalculator divide(Number other, Integer beforeOperateScale) {
        return operator(BigDecimal::divide, other, beforeOperateScale);
    }

    /**
     * Divides the current value by the specified value with a specified scale.
     *
     * @param other the value to divide by
     * @param scale the scale for the result
     * @return a {@code ChainedCalculator} instance with the updated value
     */
    public ChainedCalculator divideWithScale(Number other, Integer scale) {
        return baseOperator((otherValue) ->
                this.value.divide(otherValue, scale, RoundingMode.HALF_UP), other, null);
    }

    /**
     * Divides the current value by the specified value with a specified scale and a scale applied
     * before the operation.
     *
     * @param other              the value to divide by
     * @param scale              the scale for the result
     * @param beforeOperateScale the scale to be applied before the operation
     * @return a {@code ChainedCalculator} instance with the updated value
     */
    public ChainedCalculator divideWithScale(Number other, Integer scale, Integer beforeOperateScale) {
        return baseOperator((otherValue) ->
                        this.value.divide(otherValue, scale, RoundingMode.HALF_UP),
                other, beforeOperateScale);
    }

    /**
     * Returns the current value as a {@link BigDecimal} with the specified scale.
     *
     * @param scale the scale for the result
     * @return the current value as a {@link BigDecimal} with the specified scale
     */
    public BigDecimal getValue(int scale) {
        return value.setScale(scale, RoundingMode.HALF_UP);
    }

    /**
     * Returns the current value as a {@link BigDecimal}.
     *
     * @return the current value as a {@link BigDecimal}
     */
    public BigDecimal getValue() {
        return value;
    }

    /**
     * Returns the current value as a {@link Double}.
     *
     * @return the current value as a {@link Double}
     */
    public double getDouble() {
        return getValue().doubleValue();
    }

    /**
     * Returns the current value as a {@link Double} with the specified scale.
     *
     * @param scale the scale for the result
     * @return the current value as a {@link Double} with the specified scale
     */
    public double getDouble(int scale) {
        return getValue(scale).doubleValue();
    }

    /**
     * Returns the current value as a {@link Long}.
     *
     * @return the current value as a {@link Long}
     */
    public long getLong() {
        return getValue().longValue();
    }

    /**
     * Returns the current value as an {@link Integer}.
     *
     * @return the current value as an {@link Integer}
     */
    public int getInteger() {
        return getValue().intValue();
    }

    /**
     * Applies the specified operator function to the current value and another value.
     *
     * @param operator   the operator function to apply
     * @param otherValue the value to apply the operator with
     * @return a ChainedCalculator instance with the updated value
     */
    private ChainedCalculator operator(BiFunction<BigDecimal, BigDecimal, BigDecimal> operator, Object otherValue) {
        return operator(operator, otherValue, 9);
    }

    /**
     * Applies the specified operator function to the current value and another value with a
     * specified scale before the operation.
     *
     * @param operator           the operator function to apply
     * @param other              the value to apply the operator with
     * @param beforeOperateScale the scale to be applied before the operation,
     *                           or null if not applicable
     * @return a ChainedCalculator instance with the updated value
     */
    private ChainedCalculator operator(BiFunction<BigDecimal, BigDecimal, BigDecimal> operator,
                                       Object other,
                                       Integer beforeOperateScale) {
        return baseOperator((otherValue) ->
                        operator.apply(this.value, otherValue),
                other,
                beforeOperateScale);
    }

    /**
     * Applies the specified operator function to the current value and another
     * value.
     *
     * @param operatorFunction   the operator function to apply
     * @param anotherValue       the value to apply the operator with
     * @param beforeOperateScale the scale to be applied before the operation,
     *                           or null if not applicable
     * @return a ChainedCalculator instance with the updated value
     */
    private synchronized ChainedCalculator baseOperator(Function<BigDecimal, BigDecimal> operatorFunction,
                                                        Object anotherValue,
                                                        Integer beforeOperateScale) {
        if (Objects.isNull(anotherValue)) {
            return this;
        }
        if (anotherValue instanceof ChainedCalculator) {
            this.value = operatorFunction.apply(((ChainedCalculator) anotherValue).getValue());
            return this;
        }
        this.value = operatorFunction.apply(convertBigDecimal(anotherValue, beforeOperateScale));
        return this;
    }

    /**
     * Converts the specified value to a {@link BigDecimal}.
     *
     * @param value the value to convert
     * @param scale the scale to apply to the resulting BigDecimal, or null if not applicable
     * @return the converted BigDecimal value
     */
    private BigDecimal convertBigDecimal(Object value, Integer scale) {
        if (Objects.isNull(value)) {
            return BigDecimal.ZERO;
        }
        BigDecimal res;
        if (value instanceof Number num) {
            res = BigDecimal.valueOf(num.doubleValue());
        } else {
            try {
                res = new BigDecimal(value.toString());
            } catch (NumberFormatException ignored) {
                return BigDecimal.ZERO;
            }
        }

        if (Objects.nonNull(scale)) {
            return res.setScale(scale, RoundingMode.HALF_UP);
        }
        return res;
    }
}
