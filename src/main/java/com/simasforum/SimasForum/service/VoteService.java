package com.simasforum.SimasForum.service;

import com.simasforum.SimasForum.model.Reply;
import com.simasforum.SimasForum.model.Thread;
import com.simasforum.SimasForum.model.User;
import com.simasforum.SimasForum.model.Vote;
import com.simasforum.SimasForum.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;

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
            } else {
                voteRepository.deleteById(foundVote.getId());
                if (!foundVote.isUpVote()) {
                    thread.setVoteScore(thread.getVoteScore() + 1);
                } else {
                    thread.setVoteScore(thread.getVoteScore() - 2);
                    voteRepository.save(new Vote(thread, user, false));
                }
            }
        } else {
            if (isUpVote) {
                voteRepository.save(new Vote(thread, user, true));
                thread.setVoteScore(thread.getVoteScore() + 1);
            } else {
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
            if (isUpVote) {
                voteRepository.save(new Vote(reply, user, true));
                reply.setVoteScore(reply.getVoteScore() + 1);
            } else {
                voteRepository.save(new Vote(reply, user, false));
                reply.setVoteScore(reply.getVoteScore() - 1);
            }
        }
    }

    public Map<Long, Boolean> getUserVotedList(List<Reply> replies, Long userId) {
        //Map<replyId, isUpVote>
        Map<Long, Boolean> listVote = new HashMap<Long, Boolean>();
        for (Reply reply : replies) {
            Vote vote = voteRepository.findByReplyIdAndUserId(reply.getId(),userId);
            if(vote!=null){
                listVote.put(vote.getReply().getId(),vote.isUpVote());
            }
        }
        return listVote;
    }
}
