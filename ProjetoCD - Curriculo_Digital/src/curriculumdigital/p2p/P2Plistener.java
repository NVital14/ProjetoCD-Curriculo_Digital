/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package curriculumdigital.p2p;

import curriculumdigital.core.BlockChain;

/**
 *
 * @author noemi
 */
public interface P2Plistener {

    public void onException(Exception ex, String message);

    public void onMessage(String title, String message);

    public void onStartRemote(String message);

    public void onConect(String address);

    public void onSubmission(String transaction);

    public void onStartMining(String message, int zeros);

    public void onStopMining(String message, int nonce);

    public void onNounceFound(String message, int nonce);

    public void onBlockchainUpdate(BlockChain b);
    
    public void onNewCurriculum();
    public void onNewCurso();
}
