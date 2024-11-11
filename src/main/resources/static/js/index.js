function uploadFile(breadcrumb) {
    var path = '';
    for (var i = 0; i < breadcrumb.length; i++) {
        path += breadcrumb[i].folderName + '\\';
    }
    document.getElementById('breadcrumb').value = path;
    const uploadFileModal = new bootstrap.Modal(document.getElementById('uploadFileModal'));
    uploadFileModal.show();
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
    document.getElementById('newFolderName').value = currentFolderName;
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
