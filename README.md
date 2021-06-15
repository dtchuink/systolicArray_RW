# systolicArray_RW

## This program generates a Systolic Array with RapidWright.

1. The PE of the Array are composed of MACs operations
2. The array is doubled buffering.
3. Input FIFOs are of depth 16
4. Each DCP is implemented OOC first with vivado
5. This program is not algorithmically optimal and can be ressources hungry for large arrays (_In progress_)

![parallel_memory_approach](https://user-images.githubusercontent.com/31550692/122040190-846d8500-cda5-11eb-9370-b23c196debd9.png)
