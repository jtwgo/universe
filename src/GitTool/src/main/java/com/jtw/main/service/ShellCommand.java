package com.jtw.main.service;

import java.util.List;

public class ShellCommand implements Command
{
    @Override
    public List<String> exeCmd(String cmd)
    {
        throw new UnsupportedOperationException("shell方式尚未实现，敬请期待!");
    }
}
