We combined two methods in GridView, init and updateCells, that contained the exact same code. This was seemingly a case of convergent evolution, and an incorrect assumption that the two methods would have different requirements.

Additionally, we moved the duplicated method getRandomNeighbor to the parent Simulation class from the multiple subclasses implementing it. 

dcb31 cgu4

Part 2

Many methods in UIView were public even though they were only used internally. We fixed that.

dcb31 cds33
