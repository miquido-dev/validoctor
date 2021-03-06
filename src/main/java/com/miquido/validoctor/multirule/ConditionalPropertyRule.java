package com.miquido.validoctor.multirule;

import com.miquido.validoctor.ailment.Ailment;
import com.miquido.validoctor.rule.Rule;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

class ConditionalPropertyRule<PatientType, PropertyType> implements PropertyRule<PatientType> {

  private final String property;
  private final Function<PatientType, PropertyType> propertyGetter;
  private final Predicate<PatientType> condition;
  private final Rule<PropertyType> rule;

  ConditionalPropertyRule(String property, Function<PatientType, PropertyType> propertyGetter,
                          Predicate<PatientType> condition, Rule<PropertyType> rule) {
    this.property = property;
    this.propertyGetter = propertyGetter;
    this.condition = condition;
    this.rule = rule;
  }

  @Override
  public boolean test(PatientType obj) {
    return !condition.test(obj) || rule.test(propertyGetter.apply(obj));
  }

  @Override
  public Ailment apply(PatientType obj) {
    if (condition.test(obj)) {
      return rule.apply(propertyGetter.apply(obj));
    }
    return null;
  }

  @Override
  public Ailment peekAilment() {
    return rule.peekAilment();
  }

  @Override
  public Map<String, Object> getParams() {
    return rule.getParams();
  }

  @Override
  public String getProperty() {
    return property;
  }
}
