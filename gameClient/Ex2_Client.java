package gameClient;

import Server.Game_Server_Ex2;
import api.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Ex2_Client implements Runnable {
	private static MyFrame windowGame;
	private static Arena arenaGame;

	public static void main(String[] a) {
//		String s=a[1];
// need to get from terminal the level and id
		Thread player = new Thread(new Ex2_Client());
		player.start(); //start the game
	}

	@Override
	public void run() {
		int level = 1;
		game_service game = Game_Server_Ex2.getServer(level); // you have [0,23] games
		int id = 318926854;
		game.login(id);

		// creat the graph for the game
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(DWGraph_DS.class, new DWGraph_DS.DWGraph_DSJson());
		Gson gson = builder.create();
		directed_weighted_graph graph = gson.fromJson(game.getGraph(), DWGraph_DS.class);
		init(game);

		game.startGame();
		windowGame.setTitle("Ex2 - OOP: (NONE trivial Solution) " + game.toString());
		int ind = 0;
		long delayTime = 100;// need to think if we want changh while the game is running.

		while (game.isRunning()) {
			moveAgants(game, graph);
			try {
				if (ind % 1 == 0) {
					windowGame.repaint();
				}
				Thread.sleep(delayTime);
				ind++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String res = game.toString();

		System.out.println(res);
		System.exit(0);
	}

	/**
	 * Moves each of the agents along the edge,
	 * in case the agent is on a node the next destination (next edge) is chosen (randomly).
	 *
	 * @param game
	 * @param graph
	 * @param
	 */
	private static void moveAgants(game_service game, directed_weighted_graph graph) {
		game.move();
		List<CL_Agent> agentsList = Arena.json2Agent(game.getAgents(), graph);
		arenaGame.setAgents(agentsList);
		List<CL_Pokemon> pokemonList = Arena.json2Pokemons(game.getPokemons());
		arenaGame.setPokemons(pokemonList);

		for (CL_Agent agentPlayer : agentsList)
			if (!agentPlayer.isMoving()) {
				int id = agentPlayer.getID();
				//int src = agentPlayer.getSrcNode();
				double v = agentPlayer.getValue();
				int dest = nextNode(game,graph, agentPlayer);
				game.chooseNextEdge(agentPlayer.getID(), dest);
				System.out.println("Agent: " + id + ", val: " + v + "   turned to node: " + dest);
			}
	}

	/**
	 * walk implementation!
	 * @return
	 */
	// option to choose next node
	// 1. go to the closet - distance
	// 2. go to the closet - whaigt/speed
	// 3. go to the highest score
	// 4. to part to area
	// 5. fo the most area with pokemn


	// 1. every agent go to different pokemon
	// 2. which agent is the closet to pokemon
	// 3. which agent will arrive to pokemt fastes.
	private static int nextNode(game_service game,directed_weighted_graph g,CL_Agent agentPlayer)
	{
		int src = agentPlayer.getSrcNode();
		dw_graph_algorithms ga = new DWGrpah_Algo();
		ga.init(g);
		CL_Pokemon closetPokemon=closetPokemon(g,src);
		if(closetPokemon.getEatAgent()==null)
		{
			closetPokemon.setEatAgent(agentPlayer);
			if(ga.shortestPathDist(src, closetPokemon.get_edge().getSrc())==0)
			{
				return closetPokemon.get_edge().getDest();
			}
			return ga.shortestPath(src, closetPokemon.get_edge().getSrc()).get(1).getKey();
		}
		CL_Pokemon[] arr=closetPokemon2(g,src);
		if(src==arr[1].get_edge().getSrc())
		{
			arr[1].setEatAgent(agentPlayer);
			return arr[1].get_edge().getDest();
		}
		if(closetAgent(g,agentPlayer,closetPokemon.getEatAgent(),closetPokemon)==-1)
		{
			arr[1].setEatAgent(arr[0].getEatAgent());
			arr[0].setEatAgent(agentPlayer);
			game.chooseNextEdge(arr[0].getEatAgent().getID(),ga.shortestPath(src,arr[1].get_edge().getSrc()).get(1).getKey());
			return arr[0].get_edge().getSrc();
		}
		arr[1].setEatAgent(agentPlayer);
		return arr[1].get_edge().getSrc();


//		CL_Pokemon ClosetPokemon = arenaGame.getPokemons().get(0);
//		for (CL_Pokemon pokemon : arenaGame.getPokemons())// run on all pokmon
//		{
//			Arena.updateEdge(pokemon,g);
//			Arena.updateEdge(ClosetPokemon,g);
//			if (ga.shortestPathDist(src, pokemon.get_edge().getSrc()) <
//					ga.shortestPathDist(src, ClosetPokemon.get_edge().getSrc())) {
//				ClosetPokemon = pokemon;
//				// פה זה מוצא את הסוכן הכי קרוב אנחנו רוצים את השני
//				// למצוא את הפוקימון שהכי קרוב לסוכן שנותר ללא פוקימון. להעתיק את הפור עוד פעם
//				// הבעיה שהוא יכול שוב להיתקע על אותו פוקימון עם סוכן אחר
//				if (src == ClosetPokemon.get_edge().getSrc())
//				{
//					pokemon.setEatAgent(agentPlayer);
//					return pokemon.get_edge().getDest();
//				}
//			}
//		}
//		if(ga.shortestPathDist(src, ClosetPokemon.get_edge().getSrc())==0)
//			return ClosetPokemon.get_edge().getDest();
//		ClosetPokemon.setEatAgent(agentPlayer);
//		return ga.shortestPath(src, ClosetPokemon.get_edge().getSrc()).get(1).getKey();
	}

	public static int closetAgent (directed_weighted_graph g,CL_Agent a1, CL_Agent a2 ,CL_Pokemon p)
	{
		dw_graph_algorithms ga = new DWGrpah_Algo();
		ga.init(g);
		if(ga.shortestPathDist(a1.getSrcNode(),p.get_edge().getSrc())<
				ga.shortestPathDist(a2.getSrcNode(),p.get_edge().getSrc()))
			return -1;
		return 1;
	}

	public static CL_Pokemon closetPokemon(directed_weighted_graph g, int src)
	{
		dw_graph_algorithms ga = new DWGrpah_Algo();
		ga.init(g);

		CL_Pokemon closetPokemon = arenaGame.getPokemons().get(0);
		for(CL_Pokemon pokemon: arenaGame.getPokemons()) {
			Arena.updateEdge(pokemon, g);
			Arena.updateEdge(closetPokemon, g);
			if (ga.shortestPathDist(src, pokemon.get_edge().getSrc()) <
					ga.shortestPathDist(src, closetPokemon.get_edge().getSrc())) {
				closetPokemon = pokemon;
				if (src == closetPokemon.get_edge().getSrc()) {
					return pokemon;
				}
			}
		}
		return closetPokemon;
	}

	public static CL_Pokemon[] closetPokemon2(directed_weighted_graph g, int src)
	{
		dw_graph_algorithms ga = new DWGrpah_Algo();
		ga.init(g);
		CL_Pokemon[] arr= new CL_Pokemon[2];
		CL_Pokemon closetPokemon1 = arenaGame.getPokemons().get(0);
		CL_Pokemon closetPokemon2 = arenaGame.getPokemons().get(1);
		arr[0]=closetPokemon1;
		arr[1]=closetPokemon2;
		for(CL_Pokemon pokemon: arenaGame.getPokemons()) {
			Arena.updateEdge(pokemon, g);
			Arena.updateEdge(closetPokemon1, g);
			if (ga.shortestPathDist(src, pokemon.get_edge().getSrc()) <
					ga.shortestPathDist(src, closetPokemon1.get_edge().getSrc()))
			{
				closetPokemon1 = pokemon;
			}
			else
			{
				if (ga.shortestPathDist(src, pokemon.get_edge().getSrc()) <
						ga.shortestPathDist(src, closetPokemon2.get_edge().getSrc()))
				{
					closetPokemon2 = pokemon;
				}
			}
			if(ga.shortestPathDist(src, closetPokemon1.get_edge().getSrc())>
					ga.shortestPathDist(src, closetPokemon2.get_edge().getSrc()))
			{
				CL_Pokemon temp=closetPokemon1;
				closetPokemon1=closetPokemon2;
				closetPokemon2=closetPokemon1;
			}
			if(src==closetPokemon1.get_edge().getSrc() && src==closetPokemon2.get_edge().getSrc())
			{
				return arr;
			}
		}
		return arr;
	}


	private void init(game_service game) {
		//String g = game.getGraph();
		String pokemon = game.getPokemons();
		GsonBuilder builder=new GsonBuilder();
		builder.registerTypeAdapter(DWGraph_DS.class, new DWGraph_DS.DWGraph_DSJson());
		Gson gson=builder.create();
		directed_weighted_graph graph=gson.fromJson(game.getGraph(),DWGraph_DS.class);
		arenaGame = new Arena();
		arenaGame.setGraph(graph);
		arenaGame.setPokemons(Arena.json2Pokemons(pokemon));

		windowGame = new MyFrame("test Ex2");
		windowGame.setSize(500, 500);
		windowGame.update(arenaGame);


		windowGame.show();
		String info = game.toString();
		JSONObject line;
		try {
			line = new JSONObject(info);
			JSONObject jsonObject = line.getJSONObject("GameServer");
			int rs = jsonObject.getInt("agents");
			System.out.println(info);
			System.out.println(game.getPokemons());

			//need chose a place for agent
			int src_node = 0;  // arbitrary node, you should start at one of the pokemon

			ArrayList<CL_Pokemon> cl_pokemons = Arena.json2Pokemons(game.getPokemons());
			for(CL_Pokemon pok: cl_pokemons)
			{
				Arena.updateEdge(pok,graph);
			}

			for(int a = 0;a<rs;a++) {
				int ind = a%cl_pokemons.size();
				CL_Pokemon c = cl_pokemons.get(ind);
				int nn = c.get_edge().getDest();
				if(c.getType()<0 )
				{
					nn = c.get_edge().getSrc();
				}

				game.addAgent(nn);
			}
		}
		catch (JSONException e) {e.printStackTrace();}
	}
}
