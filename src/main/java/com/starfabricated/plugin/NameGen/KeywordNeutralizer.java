package com.starfabricated.plugin.NameGen;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import software.coley.recaf.info.ClassInfo;
import software.coley.recaf.info.member.FieldMember;
import software.coley.recaf.info.member.LocalVariable;
import software.coley.recaf.info.member.MethodMember;
import software.coley.recaf.services.mapping.gen.naming.DeconflictingNameGenerator;
import software.coley.recaf.workspace.model.Workspace;

import java.util.function.UnaryOperator;

public class KeywordNeutralizer implements DeconflictingNameGenerator {
    private Workspace workspace;
    private long varIndex = 1;

    private static final UnaryOperator<String> REPLACE_DOTS =
            name -> name.replace(".", "_dot_");

    @Override
    public void setWorkspace(@Nullable Workspace workspace) {
        this.workspace = workspace;
    }

    @Nonnull
    private String nextVarName() {
        return "var" + varIndex++;
    }

    private static String buildName(String prefix, String name) {
        return prefix + REPLACE_DOTS.apply(name);
    }

    private static String getSimpleName(String fullName) {
        return fullName.substring(fullName.lastIndexOf('/') + 1);
    }


    @Nonnull
    @Override
    public String mapClass(@Nonnull ClassInfo info) {
        String name = getSimpleName(info.getName());
        String packagePath = info.getPackageName() != null ? info.getPackageName()+"/" : "";
        return packagePath + "Class_" + REPLACE_DOTS.apply(name);
    }

    @Nonnull
    @Override
    public String mapField(@Nonnull ClassInfo owner, @Nonnull FieldMember field) {
        return buildName("field_", field.getName());
    }

    @Nonnull
    @Override
    public String mapMethod(@Nonnull ClassInfo owner, @Nonnull MethodMember method) {
        return buildName("method_", method.getName());
    }

    @Nonnull
    @Override
    public String mapVariable(@Nonnull ClassInfo owner, @Nonnull MethodMember declaringMethod, @Nonnull LocalVariable variable) {
        return nextVarName();
    }
}
