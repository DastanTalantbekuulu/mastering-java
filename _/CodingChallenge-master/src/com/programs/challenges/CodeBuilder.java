package com.programs.challenges;
public class CodeBuilder
{
	StringBuilder sb = new StringBuilder();
    public CodeBuilder(String className)
    {
    	sb.append(String.format("public class %s",className)); 
    	sb.append("\n{\n");
    }
    
    public CodeBuilder addField(String name, String type)
    {
        sb.append(String.format("  public %s %s;%n",type,name));
        return this;
    }
        
    @Override
    public String toString()
    {
        sb.append("}");
        return sb.toString();
    }
}