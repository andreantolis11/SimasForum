package com.simasforum.SimasForum.service;

import com.simasforum.SimasForum.model.Reply;
import com.simasforum.SimasForum.model.Thread;
import com.simasforum.SimasForum.model.User;
import com.simasforum.SimasForum.model.Vote;
import com.simasforum.SimasForum.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;

    private ThreadService threadService;

    @Autowired
    public void setThreadService(ThreadService threadService) {
        this.threadService = threadService;
    }

    public void addThreadVote(Thread thread, User user, boolean isUpVote) {
        Vote foundVote = voteRepository.findByThreadIdAndUserId(thread.getId(), user.getId());
        if (foundVote != null) {
            if (isUpVote) {
                voteRepository.deleteById(foundVote.getId());
                if (foundVote.isUpVote()) {
                    thread.setVoteScore(thread.getVoteScore() - 1);
                } else {
                    thread.setVoteScore(thread.getVoteScore() + 2);
                    voteRepository.save(new Vote(thread, user, true));
                }
            } else{
                voteRepository.deleteById(foundVote.getId());
                if (!foundVote.isUpVote()) {
                    thread.setVoteScore(thread.getVoteScore() + 1);
                } else {
                    thread.setVoteScore(thread.getVoteScore() - 2);
                    voteRepository.save(new Vote(thread, user, false));
                }
            }
        } else {
            if(isUpVote){
                voteRepository.save(new Vote(thread, user, true));
                thread.setVoteScore(thread.getVoteScore() + 1);
            }else{
                voteRepository.save(new Vote(thread, user, false));
                thread.setVoteScore(thread.getVoteScore() - 1);
            }
        }
    }

    public void addReplyVote(Reply reply, User user, boolean isUpVote) {
        Vote foundVote = voteRepository.findByReplyIdAndUserId(reply.getId(), user.getId());
        if (foundVote != null) {
            if (isUpVote) {
                voteRepository.deleteById(foundVote.getId());
                if (foundVote.isUpVote()) {
                    reply.setVoteScore(reply.getVoteScore() - 1);
                } else {
                    reply.setVoteScore(reply.getVoteScore() + 2);
                    voteRepository.save(new Vote(reply, user, true));
                }
            } else {
                voteRepository.deleteById(foundVote.getId());
                if (!foundVote.isUpVote()) {
                    reply.setVoteScore(reply.getVoteScore() + 1);
                } else {
                    reply.setVoteScore(reply.getVoteScore() - 2);
                    voteRepository.save(new Vote(reply, user, false));
                }
            }
        } else {
            if(isUpVote){
                voteRepository.save(new Vote(reply, user, true));
                reply.setVoteScore(reply.getVoteScore() + 1);
            } else {
                voteRepository.save(new Vote(reply, user, false));
                reply.setVoteScore(reply.getVoteScore() - 1);
            }
        }
    }
}
