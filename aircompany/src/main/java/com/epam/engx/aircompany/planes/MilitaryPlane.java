package com.epam.engx.aircompany.planes;

import com.epam.engx.aircompany.models.MilitaryType;

public record MilitaryPlane(Specification specification, MilitaryType type) implements Plane {
}
