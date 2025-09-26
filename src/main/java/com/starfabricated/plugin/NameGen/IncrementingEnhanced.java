package com.starfabricated.plugin.NameGen;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import software.coley.recaf.info.ClassInfo;
import software.coley.recaf.info.member.FieldMember;
import software.coley.recaf.info.member.LocalVariable;
import software.coley.recaf.info.member.MethodMember;
import software.coley.recaf.services.mapping.gen.naming.DeconflictingNameGenerator;
import software.coley.recaf.util.Keywords;
import software.coley.recaf.workspace.model.Workspace;

import java.util.Set;

public class IncrementingEnhanced implements DeconflictingNameGenerator {
    private Workspace workspace;
    private long classIndex = 1;
    private long fieldIndex = 1;
    private long methodIndex = 1;
    private long varIndex = 1;
    private long innerClassIndex = 1;

    private final String class_prefix;
    private final String field_prefix;
    private final String method_prefix;
    private final String var_prefix;
    private final Boolean save_package;
    private static final Set<String> keywords =Keywords.getKeywords();

    public IncrementingEnhanced (String classPrefix, String fieldPrefix, String methodPrefix, String varPrefix, boolean savePackage){
        class_prefix = classPrefix;
        field_prefix = fieldPrefix;
        method_prefix = methodPrefix;
        var_prefix = varPrefix;
        save_package = savePackage;
    }

    @Nonnull
    private String nextClassName() {
        return class_prefix + classIndex++;
    }

    @Nonnull
    private String nextInnerClassName() {
        return "InnerClass_" + innerClassIndex++;
    }

    @Nonnull
    private String nextFieldName() {
        return field_prefix + fieldIndex++;
    }

    @Nonnull
    private String nextMethodName() {
        return method_prefix + methodIndex++;
    }

    @Nonnull
    private String nextVarName() {
        return var_prefix + varIndex++;
    }

    @Nonnull
    private String getPackageName(ClassInfo info){
        if (save_package) return info.getPackageName() != null ? keywords.stream().reduce(info.getPackageName(),(pkg, keyword) -> pkg.replace(keyword, "_kwrm_"))+"/" : "";
        else return "";
    }

    @Override
    public void setWorkspace(@Nullable Workspace workspace) {
        this.workspace = workspace;
    }

    @Nonnull
    @Override
    public String mapClass(@Nonnull ClassInfo info) {
        String packageName = getPackageName(info);
            String name = nextClassName();
            if (workspace != null) {
                while (workspace.findClass(name) != null) {
                    name = nextClassName();
                }
            }
        return packageName + name;
    }

    @Nonnull
    @Override
    public String mapField(@Nonnull ClassInfo owner, @Nonnull FieldMember field) {
        String name = nextFieldName();
        String descriptor = field.getDescriptor();
        while (owner.getDeclaredField(name, descriptor) != null)
            name = nextFieldName();
        return name;
    }

    @Nonnull
    @Override
    public String mapMethod(@Nonnull ClassInfo owner, @Nonnull MethodMember method) {
        String name = nextMethodName();
        String descriptor = method.getDescriptor();
        while (owner.getDeclaredMethod(name, descriptor) != null)
            name = nextMethodName();
        return name;
    }

    @Nonnull
    @Override
    public String mapVariable(@Nonnull ClassInfo owner, @Nonnull MethodMember declaringMethod, @Nonnull LocalVariable variable) {
        return nextVarName();
    }
}
