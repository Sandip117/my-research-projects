def sum(str1, str2):
    table = [[0 for x in range(len(str1)+1)] for y in range(len(str2)+1)]

    
    for i in range(0,len(str2)+1):
        for j in range(0, len(str1)+1):
            if i == 0 or j == 0:
                table[i][j] = 0
            elif str1[j-1] == str2[i-1]:
                table[i][j] = table[i-1][j-1] + 1
            else:
                table[i][j] = max(table[i][j-1],table[i-1][j])
    return table[i][j]



if __name__ == '__main__':
    str1 = 'abbc'
    str2 = 'abbc'
