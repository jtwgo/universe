package com.jtw.main.unified.manager;

import com.jtw.main.unified.entity.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.*;

public class TokenManager
{
    private TokenManager(){}

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenManager.class);

    private static final TokenManager INSTANCE = new TokenManager();

    private static final List<Token> TOKENS = new ArrayList<>();

    public static TokenManager getInstance()
    {
        return INSTANCE;
    }

    public List<Token> queryAllToken()
    {
        List<Token> tokens_replica = new ArrayList<>(Arrays.asList(new Token[TOKENS.size()]));
        Collections.copy(tokens_replica,TOKENS);
        return tokens_replica;
    }

    /**
     * 添加token到内存中
     * @param token
     */
    public void addToken(Token token)
    {
        String username = token.getUsername();
        if (StringUtils.isEmpty(username))
        {
            LOGGER.error("token is invalid the username is:"+username);
            return;
        }

        String uuid = token.getUuid();
        if (StringUtils.isEmpty(uuid))
        {
            LOGGER.error("token is invalid the uuid is:"+uuid);
            return;
        }
        synchronized (this)
        {
            TOKENS.add(token);
        }
    }

    /**
     * 从内存中删除token
     * @param token
     */
    public void removeToken(Token token)
    {
        //uuid作为token的唯一标识
        String uuid = token.getUuid();
        Token deleteToken = null;
        if (StringUtils.isEmpty(uuid))
        {
            LOGGER.error("the token is invalid,uuid is null or empty");
            return;
        }
        synchronized (this)
        {
            for (Token t:TOKENS)
            {
                if (uuid.equals(t.getUuid()))
                {
                    deleteToken = t;
                    break;
                }
            }
            if (deleteToken != null)
            {
                TOKENS.remove(token);
                LOGGER.info("token:" + token + "is deleted from memory");
            }
            else
            {
                LOGGER.info("token:" + token + "is not exist in memory");
            }
        }

    }

    public synchronized void  removeAll(Collection<Token> tokens)
    {
        TOKENS.removeAll(tokens);
    }

    public synchronized void clearToken()
    {
        TOKENS.clear();
    }

    public synchronized Token findByUUID(String uuid)
    {
        for (Token token :TOKENS)
        {
            if (token.getUuid().equals(uuid))
            {
                return token;
            }
        }
        return null;
    }
}
