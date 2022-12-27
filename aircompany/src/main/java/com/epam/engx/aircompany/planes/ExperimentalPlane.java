package com.epam.engx.aircompany.planes;

import com.epam.engx.aircompany.models.ClassificationLevel;

public record ExperimentalPlane(Specification specification, ClassificationLevel classificationLevel) implements Plane {
}
