package com.nikialeksey.nullfree;

import com.nikialeksey.functions.Function0;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Git {

    private final Function0<Repository, NullfreeException> repo;

    public Git(final File gitDir) {
        this(
            () -> {
                try {
                    return new FileRepositoryBuilder()
                        .setGitDir(gitDir)
                        .setMustExist(true)
                        .build();
                } catch (IOException e) {
                    throw new NullfreeException(
                        String.format(
                            "Can not build the git repo from git folder: %s",
                            gitDir.getAbsolutePath()
                        ),
                        e
                    );
                }
            }
        );
    }

    public Git(final Function0<Repository, NullfreeException> repo) {
        this.repo = repo;
    }

    public Origin origin() throws NullfreeException {
        try {
            return new Origin(
                new URL(
                    repo.execute()
                        .getConfig()
                        .getString("remote", "origin", "url")
                )
            );
        } catch (MalformedURLException e) {
            throw new NullfreeException("Can not form the origin url.", e);
        }
    }
}
