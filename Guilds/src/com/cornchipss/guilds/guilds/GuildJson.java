package com.cornchipss.guilds.guilds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Chunk;

import com.cornchipss.guilds.util.Serializer;
import com.cornchipss.guilds.util.Vector3;

public class GuildJson 
{
	private String guildName;
	private Map<String, GuildRank> members;
	private Map<String, GuildRelation> relations;
	private List<Vector3<String, Integer, Integer>> ownedChunks;
	private String guildHome;
	private double balance;
	
	public GuildJson(String name, Map<String, GuildRank> members, Map<String, GuildRelation> relations, List<Vector3<String, Integer, Integer>> ownedChunkLocations, String homeLocation, double balance)
	{
		this.guildName = name;
		this.members = members;
		this.ownedChunks = ownedChunkLocations;
		this.guildHome = homeLocation;
		this.relations = relations;
		this.balance = balance;
	}
	
	public Guild toGuild()
	{
		Map<UUID, GuildRank> membersComplete = new HashMap<>();
		for(String s : members.keySet())
		{
			membersComplete.put(UUID.fromString(s), members.get(s));
		}
		
		return new Guild(guildName, membersComplete, Serializer.deserializeChunks(ownedChunks), Serializer.deserializeLocation(guildHome), balance);
	}
	
	public static GuildJson fromGuild(Guild g)
	{
		Map<String, GuildRank> memberUUIDStrings = new HashMap<>();
		for(UUID id : g.getMembersFull().keySet())
		{
			memberUUIDStrings.put(id.toString(), g.getMembersFull().get(id));
		}
		
		List<Vector3<String, Integer, Integer>> ownedChunkLocations = new ArrayList<>();
		
		for(Chunk c : g.getOwnedChunks())
		{
			ownedChunkLocations.addAll(Serializer.serializeChunk(c));
		}
		
		String homeLocation = Serializer.serializeLocation(g.getHome());
		
		Map<String, GuildRelation> relations = new HashMap<>();
		for(Guild guildRelation : g.getAllRelations().keySet())
		{
			relations.put(guildRelation.getName(), g.getAllRelations().get(guildRelation));
		}
		
		return new GuildJson(g.getName(), memberUUIDStrings, relations, ownedChunkLocations, homeLocation, g.getBalance());
	}
	
	public Map<Guild, GuildRelation> getRelations(List<Guild> allGuilds)
	{
		Map<Guild, GuildRelation> relationsComplete = new HashMap<>();
		
		Set<String> relationsNames = relations.keySet();
		
		for(Guild g : allGuilds)
		{
			if(relationsNames.contains(g.getName()))
			{
				relationsComplete.put(g, relations.get(g.getName()));
			}
		}
		
		return relationsComplete;
	}
}
