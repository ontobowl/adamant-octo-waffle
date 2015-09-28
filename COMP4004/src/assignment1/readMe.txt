Graham Burry
100861381
Github account: https://github.com/ontobowl
COMP4004 Repo: https://github.com/ontobowl/adamant-octo-waffle

Features:

- determine how many players are participating in this round (which defines the valid player ids for this round)
Test methods: Game::testValidNumPlayers()

- input each player's hand, in the form of a player id followed by 5 cards separated by spaces, each card being identified according to the format RankSuit. So the ace of spades will be AceSpades, and the two of Heart will be TwoHearts. 
Test methods: Game::testInputPlayerHand(), Game::testInvalidPlayerHand(), Game::testDuplicateCards, Card::testGetRank(), Card::testGetSuit(), Hand::testNumCards(), Hand::testDuplicateCard(), Hand:testInvalidID(), Hand::testParseHand(), Hand::testParseHandFromString

- output these hands (player id and cards) AND a rank (1 being the winner) in sorted decreasing order.
Test methods: Game::testGameRound, Game::testMultipleRounds, Game::testGetRanking, Card::testPrintCard(), Card::testParseCardFromString(), Card::testCardEquals(), Hand::testHandCompare(), Hand::testHandToString(), Hand::testGetHandRanking(), 
