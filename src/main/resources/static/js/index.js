function openFolder(folderName) {
    alert('打开文件夹: ' + folderName);
}
function updateFileName() {
    const fileInput = document.getElementById('fileInput');
    const fileNameInput = document.getElementById('fileName');
    if (fileInput.files.length > 0) {
        fileNameInput.value = fileInput.files[0].name;
    } else {
        fileNameInput.value = '';
    }
}
function openRenameModal(fileId, currentName) {
    document.getElementById('fileId').value = fileId;
    document.getElementById('newName').value = currentName;
    const renameModal = new bootstrap.Modal(document.getElementById('renameModal'));
    renameModal.show();
}
function openDeleteConfirmModal(fileId) {
    document.getElementById('deleteFileId').value = fileId;
    const deleteConfirmModal = new bootstrap.Modal(document.getElementById('deleteConfirmModal'));
    deleteConfirmModal.show();
}

function openRenameFolderModal(folderId, currentFolderName) {
    document.getElementById('folderId').value = folderId;
    document.getElementById('newFolderName').value = currentFolderName; // 填充当前名称
    const renameFolderModal = new bootstrap.Modal(document.getElementById('renameFolderModal'));
    renameFolderModal.show();
}

function openDeleteFolderConfirmModal(folderId) {
    document.getElementById('deleteFolderId').value = folderId;
    const deleteFolderConfirmModal = new bootstrap.Modal(document.getElementById('deleteFolderConfirmModal'));
    deleteFolderConfirmModal.show();
}

function setFolderId(folderId) {
    document.getElementById('createCurrentFolderId').value = folderId;
    const createFolderModal = new bootstrap.Modal(document.getElementById('createFolderModal'));
    createFolderModal.show();
}